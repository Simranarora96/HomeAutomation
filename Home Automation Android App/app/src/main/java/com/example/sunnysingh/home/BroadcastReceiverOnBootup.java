package com.example.sunnysingh.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sunny Singh on 2/8/2018.
 */

public class BroadcastReceiverOnBootup extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(BroadcastReceiverOnBootup.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, PeriodicService.class));;
    }
}
