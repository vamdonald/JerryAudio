package com.donald.jerryaudio.audio;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

public class OboeAudio extends AudioRecorder {

    public OboeAudio(int sampleRate, int channelNumber) {
        super(sampleRate, channelNumber);
    }

    public OboeAudio(int sampleRate, int channelNumber, DataListener listener) {
        super(sampleRate, channelNumber, listener);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }

    private native static boolean create();
    private native static void delete();
    private native static boolean setEffectOn(boolean isEffectOn);
    private native static void setRecordingDeviceId(int deviceId);
    private native static void setPlaybackDeviceId(int deviceId);
    private native static boolean setAPI(int apiType);
    private native static void native_setDefaultStreamValues(int defaultSampleRate, int defaultFramesPerBurst);

    static void setDefaultStreamValues(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            AudioManager myAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            int defaultSampleRate = Integer.parseInt(sampleRateStr);
            String framesPerBurstStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            int defaultFramesPerBurst = Integer.parseInt(framesPerBurstStr);

            native_setDefaultStreamValues(defaultSampleRate, defaultFramesPerBurst);
        }
    }

    static {
        System.loadLibrary("liveEffect");
    }

}
