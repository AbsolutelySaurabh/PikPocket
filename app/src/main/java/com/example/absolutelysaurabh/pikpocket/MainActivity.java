package com.example.absolutelysaurabh.pikpocket;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor proximitySensor, gyroscopeSensor, accelero, linearaccn, LightSensor;
    ImageView iv;
    TextView textLIGHT_available, textLIGHT_reading, gyro1, gyro2, gyro3, accelero1, accelero2, proximityText, accelero3, LIGHT_max, linear;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    public float prevproximity = 1, gyro_X, gyro_Y, gyro_Z;
    public float maxRange, filterAngle;
    public static float accelerometer, linearacceleration, currproximity, light, gyroscope;

    double dt = 0.02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView settings = (ImageView) findViewById(R.id.settings_image);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);

            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        LightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (LightSensor != null) {

            maxRange = LightSensor.getMaximumRange();
            mSensorManager.registerListener(
                    SensorListener,
                    LightSensor,
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        }
        gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor != null) {
            mSensorManager.registerListener(
                    SensorListener,
                    gyroscopeSensor,
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }
        accelero = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelero != null) {

            mSensorManager.registerListener(
                    SensorListener,
                    accelero,
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }

        linearaccn = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (linearaccn != null) {
            mSensorManager.registerListener(
                    SensorListener,
                    linearaccn,
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }

        proximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor != null) {
            mSensorManager.registerListener(
                    SensorListener,
                    proximitySensor,
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }

    }

    public void startMethod(View v){

        Intent i  =new Intent(this, AppService.class);
        startService(i);

    }

    public void stopMethod(View v){



    }


    private final SensorEventListener SensorListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

                getLight(event);

            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                float[] values = event.values;
                // Movement
                float x = values[0];
                gyro_X = x;
                float y = values[1];
                gyro_Y = y;
                float z = values[2];
                gyro_Z = z;

                gyroscope = (float) (57.3 * (float) Math.sqrt(x * x + y * y + z * z));


            } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                getAccelerometer(event);

            } else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

                float[] values = event.values;
                // Movement
                float x = values[0];
                float y = values[1];
                float z = values[2];

                linearacceleration = (float) Math.sqrt(x * x + y * y + z * z);

            } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

                getProximity(event);

            }

//            int queue[100];

            int[] arr = new int[100];
            int i = 0, max = 0, min = 0;
            float filterAngle;
            int[] queue = new int[100];
            i++;


            if(i==100)

        {
            for (i = 0; i < 99; i++) {
                queue[i] = queue[i + 1];
            }
            queue[i] = (int) gyro_X;

            if (queue[i] > max) {
                max = queue[i];
            }

            if (queue[i] < min) {
                min = queue[i];
            }

            i++;
        }

            else

        {
            queue[i] = (int) gyro_Y;

            if (queue[i] > max) {
                max = queue[i];
            }

            if (queue[i] < min) {
                min = queue[i];
            }
            i++;
        }

        currproximity =gyro_Z;
        filterAngle =

        comp_filter(accelerometer, gyroscope);

            if(light<(maxRange/1638))

        {

            if (currproximity == 1 && prevproximity == 0) {

                if (filterAngle > 2.3) {

                    //VIBRATION ALERT
                    //SOUND ALERT
                }
            }

            if (currproximity == 1 && prevproximity == 1) {

                if (filterAngle > 3) {

                    //VIBRATION ALERT
                    //SOUND ALERT
                }

            }

            if (currproximity == 0 && prevproximity == 0) {
                if (max - min > 25) {
                    //VIBRATION ALERT
                    //SOUND ALERT
                }
            }
        }

        prevproximity =currproximity;

    }
};

    float comp_filter(float accelerometer, float gyroscope) {

        float x1 = 0;
        float x2 = 0;
        float x3 = 0;
        float timeConstant = (float) 0.5;

        x1 = (accelerometer - filterAngle) * timeConstant * timeConstant;
        x3 += x1 * dt;
        x2 = x3 + ((accelerometer - filterAngle) * 2 * timeConstant) + gyroscope;
        filterAngle = (float) ((x2 * dt) + filterAngle);

        return filterAngle;



        }

    private void getProximity(SensorEvent event){

        if(event.values[0] == 0){

            currproximity = 0;

        }else{
            currproximity = 1;
        }

    }

    private void getLight(SensorEvent event){

        light = event.values[0];

    }

    private void getAccelerometer(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        accelerometer = (float) Math.sqrt(x * x + y * y + z * z);

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        mSensorManager.registerListener(this, accelero, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        mSensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        mSensorManager.registerListener(this, linearaccn, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        mSensorManager.registerListener(this, LightSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

    }

    @Override
    protected void onPause() {

        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

    }

}
