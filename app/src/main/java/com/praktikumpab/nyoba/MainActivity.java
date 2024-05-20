package com.praktikumpab.nyoba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_proximity).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProximityActivity.class)));
        findViewById(R.id.button_light).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LightActivity.class)));
        findViewById(R.id.button_touch).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TouchActivity.class)));
        findViewById(R.id.button_microphone).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MicrophoneActivity.class)));
    }
}
