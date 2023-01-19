package com.example.kr2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {
    public SensorManager sensorManager;
    public DrawView playField;
    public Sensor sensorAccel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Inst.initDpi(100, 100, 100);
        playField = new DrawView(this);
        setContentView(playField);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Intent intent = getIntent();
        playField.count = intent.getIntExtra(MainActivity.ACCESS, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        playField.startDraw();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
        playField.stopDraw();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(MainActivity.ACCESS, playField.count);
        setResult(RESULT_OK, i);
        finish();
    }

    public SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                playField.valuesAccel[0] = event.values[0];
                playField.valuesAccel[1] = event.values[1];
            }
        }
    };
}
