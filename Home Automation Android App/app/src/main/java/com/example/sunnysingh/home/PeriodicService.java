package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/8/2018.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.TRUE;

/**
     * Created by Sunny Singh on 7/29/2017.
     */

    public class PeriodicService extends Service {
        //connect to client
        //publish message
        //start subscriber
        //wait for message for 5 secs
        //send notification that device is off line
        //do this periodically
        //on notification click open Mainactivity
        //in mainactivity check this staus and then load corresponding layouts
        TimerTask doAsynchronousTask;
        final Handler handler = new Handler();
        final String serverUri = "tcp://test.mosquitto.org:1883";
        final String clientid = "homeautomation";
        final String publishtopic = "homestationapp101";
        final String subscribetopic = "checkmomdevice";
        private static final String TAG = "Offline Notification";
        private static final int Notification_id= 101;

    int Attempts =0;
    public int counter=0;
    public PeriodicService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }
    public PeriodicService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer(intent);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer(Intent intent) {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask(intent);

        //schedule the timer, to wake up every 1 second 10, 20000
        timer.schedule(timerTask, 1, 20000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask(Intent intent) {
        final Intent myintent = intent;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        ConnectivityManager cm =
                                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();
                        if(isConnected == TRUE) {
                            if (Attempts >= 0 && Attempts <= 2) {
                                Log.d("Attempts", Integer.toString(Attempts));

                                try

                                {


                                    MemoryPersistence memoryPersistence = new MemoryPersistence();
                                    final MqttAndroidClient client = new MqttAndroidClient(getBaseContext(), serverUri, clientid, memoryPersistence);

                                    try {
                                        client.connect(null, new IMqttActionListener() {
                                            @Override
                                            public void onSuccess(IMqttToken asyncActionToken) {
                                                try {
                                                    String check = "status"; // Set the first name/pair
                                                    byte[] encodedPayload = new byte[0];
                                                    encodedPayload = check.getBytes("UTF-8");
                                                    MqttMessage message = new MqttMessage(encodedPayload);
                                                    message.setId(320);
                                                    message.setRetained(true);
                                                   client.publish(publishtopic, message);
                                                   Attempts++;
                                                    Log.d("message", "published");
                                                    client.subscribe(subscribetopic,0);


                                                    client.setCallback(new MqttCallbackExtended() {
                                                        @Override
                                                        public void connectComplete(boolean b, String s) {

                                                        }

                                                        @Override
                                                        public void connectionLost(Throwable throwable) {

                                                        }

                                                        @Override
                                                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                                                            Log.d("message arrived", mqttMessage.toString());
                                                            client.unsubscribe(subscribetopic);
                                                           // client.disconnect();
                                                           Attempts = 0;
                                                            NotificationManager notificationManager =
                                                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                            notificationManager.cancel(TAG,Notification_id);

                                                        }

                                                        @Override
                                                        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                                                        }
                                                    });
                                                } catch (MqttException e) {
                                                    e.printStackTrace();
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();

                                                }
                                            }

                                            @Override
                                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                                            }
                                        });

                                    } catch (MqttException e) {
                                        e.printStackTrace();

                                    }

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }


                            } else {

                                Intent intents = new Intent(getBaseContext(), MainActivity.class);
                                PendingIntent resultIntent = PendingIntent.getActivity(getBaseContext(), 0, intents,
                                        PendingIntent.FLAG_ONE_SHOT);

                                Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                NotificationManager notificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_views);
                                contentView.setImageViewResource(R.id.image, R.drawable.remotecontrol);
                                contentView.setTextViewText(R.id.title, "Hub Offline!");
                                contentView.setTextViewText(R.id.text, "You won't receive notifications.");
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext())
                                        .setSmallIcon(R.drawable.remotecontrol)
                                        .setContent(contentView)
                                        .setContentIntent(resultIntent);


                                Notification notification = mBuilder.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(TAG,Notification_id, notification);
                                Attempts = 0;

                            }
                        }else
                        {
                            Intent intents = new Intent(getBaseContext(), MainActivity.class);
                            PendingIntent resultIntent = PendingIntent.getActivity(getBaseContext(), 0, intents,
                                    PendingIntent.FLAG_ONE_SHOT);

                            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_views);
                            contentView.setImageViewResource(R.id.image, R.drawable.remotecontrol);
                            contentView.setTextViewText(R.id.title, "Offline!");
                            contentView.setTextViewText(R.id.text, "Connect to the internet.");
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext())
                                    .setSmallIcon(R.drawable.remotecontrol)
                                    .setContent(contentView)
                                    .setContentIntent(resultIntent);


                            Notification notification = mBuilder.build();
                            notification.flags |= Notification.FLAG_AUTO_CANCEL;
                            notificationManager.notify(TAG,Notification_id, notification);

                        }
                    }
                });

            }

        };

    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
