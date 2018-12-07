package com.example.sunnysingh.home;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Sunny Singh on 1/6/2017.
 */

public class Splash_Screen extends Activity {
    DatabaseHandler db = new DatabaseHandler(this);

    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                Intent i = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }



}

