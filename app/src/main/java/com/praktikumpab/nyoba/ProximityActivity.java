package com.praktikumpab.nyoba;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private TextView sensorInfoTextView;
    private Vibrator vibrator;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        layout = findViewById(R.id.proximityLayout);
        sensorInfoTextView = findViewById(R.id.proximitySensorInfoTextView);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            sensorInfoTextView.setText("Proximity sensor not available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            if (distance < proximitySensor.getMaximumRange()) {
                layout.setBackgroundColor(Color.RED);
                sensorInfoTextView.setText("Object too close!");
                vibratePhone();
            } else {
                layout.setBackgroundColor(Color.WHITE);
                sensorInfoTextView.setText("Proximity Sensor Demo");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    private void vibratePhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }
}
