package com.praktikumpab.nyoba;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class MicrophoneActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private Button recordButton;
    private TextView  sensorInfoTextView;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private boolean isRecording = false;
    private String recordFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);

        sensorInfoTextView = findViewById(R.id.microphoneSensorInfoTextView);
        recordButton = findViewById(R.id.recordButton);

        recordButton.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
            } else {
                startRecording();
            }
        });

        checkAudioPermission();
    }

    private void checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    private void startRecording() {
        recordButton.setText("Stop Recording");
        isRecording = true;

        recordFilePath = getExternalCacheDir().getAbsolutePath() + "/audiorecord.3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        recordButton.setText("Start Recording");
        isRecording = false;

        recorder.stop();
        recorder.release();
        recorder = null;

        playRecording();
    }

    private void playRecording() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordButton.setEnabled(true);
            } else {
                recordButton.setEnabled(false);
            }
        }
    }
}
