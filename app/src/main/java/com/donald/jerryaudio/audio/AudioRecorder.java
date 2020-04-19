package com.donald.jerryaudio.audio;

public abstract class AudioRecorder {
    private int sampleRate;
    private int channelNumber;
    private DataListener dataListener;

    private AudioRecorder() {
    }

    public AudioRecorder(int sampleRate, int channelNumber) {
        this(sampleRate, channelNumber, null);
    }

    public AudioRecorder(int sampleRate, int channelNumber, DataListener listener) {
        this.sampleRate = sampleRate;
        this.channelNumber = channelNumber;
        this.dataListener = listener;
    }

    public abstract void init();

    public abstract void start();

    public abstract void stop();

    public abstract void release();

    public void setListener(DataListener listener) {
        dataListener = listener;
    }

    public DataListener getListener() {
        return dataListener;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    void validateParameters() {
        if (sampleRate <= 0 || channelNumber <= 0) {
            throw new IllegalArgumentException("Invalid audio parameters: sample rate "
                    + sampleRate + ", channel number " + channelNumber);
        }
    }

    interface DataListener {
        void onAudioAvailable(byte[] data, int sizeInByte);
    }

}
