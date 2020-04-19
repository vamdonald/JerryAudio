package com.donald.jerryaudio.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.donald.jerryaudio.R;
import com.donald.jerryaudio.audio.AudioRecorder;
import com.donald.jerryaudio.audio.JavaAudio;

public class MainActivity extends AppCompatActivity {

    AudioRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recorder = new JavaAudio(16000, 1);
        recorder.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recorder.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recorder.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.release();
    }
}
