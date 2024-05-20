package com.praktikumpab.nyoba;

import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TouchActivity extends AppCompatActivity {

    private TextView sensorInfoTextView;
    private Vibrator vibrator;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        layout = findViewById(R.id.touchLayout);
        sensorInfoTextView = findViewById(R.id.touchSensorInfoTextView);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sensorInfoTextView.setText("Touch detected");
                    vibratePhone();
                }
                return true;
            }
        });
    }

    private void vibratePhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }
}
