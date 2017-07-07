package com.example.absolutelysaurabh.pikpocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView volume_settings = (ImageView) findViewById(R.id.volume_settings_image);
        volume_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Volume_Settings_Activity.class);
                startActivity(intent);
            }
        });

        ImageView sos_settinfgs = (ImageView) findViewById(R.id.sos_settings_image);
        sos_settinfgs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SosSettingsActivity.class);
                startActivity(intent);
            }
        });


    }

}
