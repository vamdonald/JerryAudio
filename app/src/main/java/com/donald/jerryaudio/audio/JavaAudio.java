package com.donald.jerryaudio.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.donald.jerryaudio.log.JerryLog;

public class JavaAudio implements AudioRecorder, Runnable {
    private static final String TAG = JavaAudio.class.getSimpleName();

    private int sampleRate;
    private int channelNum;
    private int recBufSize;

    private volatile boolean isCapture;

    private AudioRecord audioRecord;
    private Thread recordThread;
    private byte[] captureBuf;

    private DataListener listener;

    @Override
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    @Override
    public void setChannel(int channelNum) {
        this.channelNum = channelNum;
    }

    @Override
    public void setListener(DataListener listener) {
        this.listener = listener;
    }

    @Override
    public void init() {
        if (sampleRate <= 0 || channelNum <= 0) {
            throw new IllegalArgumentException("Invalid audio parameters");
        }
        recBufSize = AudioRecord.getMinBufferSize(
                sampleRate, getChannelFormat(), AudioFormat.ENCODING_PCM_16BIT);

        if (recBufSize == AudioRecord.ERROR_BAD_VALUE || recBufSize < 0) {
            throw new IllegalArgumentException("AudioRecord buffer size invalid: " + recBufSize);
        }
        captureBuf = new byte[recBufSize];

        JerryLog.d(TAG, "init: sampleRate %d, channelNum %d, recBufSize %d",
                sampleRate, channelNum, recBufSize);
    }

    private int getChannelFormat() {
        return channelNum == 1 ? AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;
    }

    @Override
    public void start() {
        isCapture = true;

        audioRecord = getAudioRecord();
        audioRecord.startRecording();

        recordThread = new Thread(this, "JavaAudio");
        recordThread.start();
        JerryLog.d(TAG, "audio capture started");
    }

    private AudioRecord getAudioRecord() {
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRate, getChannelFormat(), AudioFormat.ENCODING_PCM_16BIT, recBufSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            JerryLog.e(TAG, "Audio record init failed.");
            throw new IllegalStateException("AudioRecord not in initialized state");
        }
        return record;
    }

    @Override
    public void stop() {
        if (!isCapture) {
            JerryLog.w(TAG, "already stopped");
            return;
        }

        isCapture = false;
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

        try {
            recordThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recordThread = null;
        JerryLog.d(TAG, "audio record stopped");
    }

    @Override
    public void release() {
    }

    @Override
    public void run() {
        if (audioRecord == null || captureBuf == null) {
            JerryLog.e(TAG, "Audio Capture is not well prepared, return");
            return;
        }

        while (isCapture) {
            int read = audioRecord.read(captureBuf, 0, recBufSize);

            if (listener != null) {
                listener.onAudioAvailable(captureBuf, read);
            }
            JerryLog.v(TAG, "audio capture size %s ", read);
        }
    }
}

