package com.example.absolutelysaurabh.pikpocket;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class Volume_Settings_Activity extends AppCompatActivity {

    private SeekBar volumeSeekBar, vibrationSeekbar;
    private AudioManager audioManager;
    private RadioButton onRadio;
    private RadioButton offRadio;
    private RadioGroup radio_vibration_group;

    private SensorManager mSensorManager;
    private Sensor proximitySensor, gyroscopeSensor, accelero,linearaccn, LightSensor;
    ImageView iv;
    TextView textLIGHT_available, textLIGHT_reading, gyro1,gyro2,gyro3, accelero1, accelero2,proximityText, accelero3, LIGHT_max, linear;

    private boolean color = false;
    private View view;
    private long lastUpdate;

    public static float prevProximity = 1;
    public float maxRange;

    public static float accelerometer,linearacceleration, currproximity, light, gyroscope;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume__settings_);

        radio_vibration_group=(RadioGroup)findViewById(R.id.radioGroup);
        onRadio= (RadioButton) findViewById(R.id.on_radio);
        offRadio= (RadioButton) findViewById(R.id.off_radio);




        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();

//        if(onRadio.isChecked()){
//
//            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            v.vibrate(2000);
//
//        }else if(offRadio.isChecked()){
//
//            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            v.cancel();
//        }

    }

    private void initControls() {

        try {
            volumeSeekBar = (SeekBar) findViewById(R.id.volume_seekbar);
//            vibrationSeekbar = (SeekBar) findViewById(R.id.vibration_seekbar);

            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

//            vibrationSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//            vibrationSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // TODO Auto-generated method stub
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

                }
            });

        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
