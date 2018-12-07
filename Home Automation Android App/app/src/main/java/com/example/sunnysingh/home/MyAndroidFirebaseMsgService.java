package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    DatabaseHandler db = new DatabaseHandler(this);

    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification

        createNotification(remoteMessage.getData());

    }
@Override
public void handleIntent(Intent intent) {


    Log.d("Crucial", intent.getExtras().toString());
    String msg = intent.getExtras().get("message").toString();//jsonObject.getString("message");
    String timestamp = intent.getExtras().get("timestamp").toString();//jsonObject.getString("timestamp");
    Intent intents = new Intent(this, ResultActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intents,
            PendingIntent.FLAG_ONE_SHOT);

    Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_views);
    contentView.setImageViewResource(R.id.image, R.drawable.remotecontrol);
    contentView.setTextViewText(R.id.title, msg);
    contentView.setTextViewText(R.id.text, timestamp);

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.remotecontrol)
            .setContent(contentView)
            .setContentIntent(resultIntent)
            .setSound(notificationSoundURI);

    Notification notification = mBuilder.build();
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    notification.defaults |= Notification.DEFAULT_SOUND;
    notification.defaults |= Notification.DEFAULT_VIBRATE;
    notificationManager.notify(1, notification);


    // notificationManager.notify(0, mNotificationBuilder.build());
    Log.d("message", msg);
    Log.d("timestamp", timestamp);
    if (msg.equals("Door Unlocked") || msg.equals("Door Locked")) {
        db.addmessage(new Contact(msg, timestamp));

    }
}   private void createNotification( Map<String,String> messageBody) {

        try {

           // JSONObject jsonObject = new JSONObject(messageBody);
            Log.d("Crucial",messageBody.get("message"));
            String msg = messageBody.get("message");//jsonObject.getString("message");
            String timestamp = messageBody.get("timestamp");//jsonObject.getString("timestamp");
            Intent intent = new Intent( this , ResultActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_views);
            contentView.setImageViewResource(R.id.image, R.drawable.remotecontrol);
            contentView.setTextViewText(R.id.title, msg);
            contentView.setTextViewText(R.id.text, timestamp);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.remotecontrol)
                    .setContent(contentView)
                    .setContentIntent(resultIntent)
                    .setSound(notificationSoundURI);

            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notificationManager.notify(1, notification);






           // notificationManager.notify(0, mNotificationBuilder.build());
            Log.d("message",msg);
            Log.d("timestamp",timestamp);
            if (msg.equals("Door Unlocked") || msg.equals("Door Locked")) {
                db.addmessage(new Contact(msg, timestamp));
            }
        }catch (Exception e)//JSONException e)
        {
            Log.d("JSON Exception",e.toString());
        }


    }
}