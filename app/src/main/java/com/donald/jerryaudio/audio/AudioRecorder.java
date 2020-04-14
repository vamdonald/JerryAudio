package com.donald.jerryaudio.audio;

public interface AudioRecorder {
    void init();

    void start();

    void stop();

    void release();

    void setSampleRate(int sampleRate);

    void setChannel(int channelNum);

    void setListener(DataListener listener);

    interface DataListener {
        void onAudioAvailable(byte[] data, int sizeInByte);
    }

}
