package com.example.absolutelysaurabh.pikpocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AppService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        return super.onStartCommand(intent, flags, startId);
//        Intent i = new Intent(getBaseContext(), MainActivity.class);
//        startActivity(intent);

//        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
