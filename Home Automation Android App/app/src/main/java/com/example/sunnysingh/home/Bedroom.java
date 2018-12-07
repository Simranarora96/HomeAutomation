package com.example.sunnysingh.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Sunny Singh on 1/3/2017.
 */

public class Bedroom extends AppCompatActivity {

    private OutputStream outStream = null;

    /* input stream used to read data from Bluetooth module */
    // private InputStream inStream;
    BluetoothSocket btsocket;

    RelativeLayout Fanon;
    RelativeLayout Fanoff;
    RelativeLayout Ledceilingon;
    RelativeLayout Ledceilingoff;
    RelativeLayout Ledwoodon;
    RelativeLayout Ledwoodoff;
    RelativeLayout Ledstripon;
    RelativeLayout LedStripoff;
    SharedPreferences sharedpreferences;
    public static final String Mypreferencesfan="Myprefsfan";
    public static final String Mypreferencesledceiling="Myprefsledceiling";
    public static final String Mypreferencesledwood="Myprefledwood";
    public static final String Mypreferencesledstrip="Myprefledstrip";
    public static final String fan="";
    public static final String ledceiling="";
    public static final String ledwood="";
    public static final String ledstrip="";
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedroom);
        btsocket = MainActivity.BTSocket.getSocket();
        try {
            outStream = btsocket.getOutputStream();
            // inStream = btsocket.getInputStream();
        } catch (IOException e) {
            Log.d("","outsream exception raised");
        }
        btnSpeak = (ImageView) findViewById(R.id.btnSpeak);

        Log.d("fan",fan);
        Log.d("ledceiling",ledceiling);
        Log.d("ledwood",ledwood);
        Log.d("ledstrip",ledstrip);
        SharedPreferences prefsfan = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE);
        SharedPreferences prefsledceiling = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE);
        SharedPreferences prefsledwood = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE);
        SharedPreferences prefsledstrip = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE);
        String fanstatus = prefsfan.getString(fan, null);
        String ledstripstatus = prefsledstrip.getString(ledstrip, null);

        String ledceilingstatus = prefsledceiling.getString(ledceiling, null);
        String ledwoodstatus = prefsledwood.getString(ledwood, null);
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);

    /*    if(prefs.getString(first,null)==null)
        {
            Log.i("cool:","inloop");

            Fanoff.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            Ledwoodoff.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(first,"cool");
            editor.commit();
            Log.i("cool:",prefs.getString(first,null));

        }*/
        if(fanstatus!=null && ledceilingstatus!=null && ledwoodstatus!=null && ledstripstatus!=null) {
            /*if(tv.equals("") && settopbox.equals("") && powerswitch.equals(""))
            {
                Fanoff.setVisibility(View.VISIBLE);
                Ledceilingoff.setVisibility(View.VISIBLE);
                Ledwoodoff.setVisibility(View.VISIBLE);
            }*/
            if (fanstatus.equals("on")) {
                Fanon.setVisibility(View.VISIBLE);
                Fanoff.setVisibility(View.INVISIBLE);
            }
            if (fanstatus.equals("off")) {
                Fanon.setVisibility(View.INVISIBLE);
                Fanoff.setVisibility(View.VISIBLE);
            }
            if (ledceilingstatus.equals("on")) {
                Ledceilingon.setVisibility(View.VISIBLE);
                Ledceilingoff.setVisibility(View.INVISIBLE);
            }
            if (ledceilingstatus.equals("off")) {
                Ledceilingon.setVisibility(View.INVISIBLE);
                Ledceilingoff.setVisibility(View.VISIBLE);
            }
            if (ledwoodstatus.equals("on")) {
                Ledwoodon.setVisibility(View.VISIBLE);
                Ledwoodoff.setVisibility(View.INVISIBLE);
            }
            if (ledwoodstatus.equals("off")) {
                Ledwoodon.setVisibility(View.INVISIBLE);
                Ledwoodoff.setVisibility(View.VISIBLE);
            }
            if (ledstripstatus.equals("on")) {
                Ledstripon.setVisibility(View.VISIBLE);
                LedStripoff.setVisibility(View.INVISIBLE);
            }
            if (ledstripstatus.equals("off")) {
                Ledstripon.setVisibility(View.INVISIBLE);
                LedStripoff.setVisibility(View.VISIBLE);
            }
        }

        else{
            Ledwoodoff.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            Fanoff.setVisibility(View.VISIBLE);
            LedStripoff.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor1 = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();
            SharedPreferences.Editor editor2 = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();
            SharedPreferences.Editor editor3= getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();
            SharedPreferences.Editor editor4= getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();
            editor1.putString(fan,"off");
            editor1.commit();
            editor2.putString(ledceiling,"off");
            editor2.commit();
            editor3.putString(ledwood,"off");
            editor3.commit();
            editor4.putString(ledstrip,"off");
            editor4.commit();

        }


        // Keep listening to the InputStream until an exception occurs
/* uncomment this to recieve message from hc05
    final Handler handler  = new Handler();
     Runnable runnable = new Runnable() {

         @Override
         public void run() {
             while (true) {
                 try {
                     // Read from the InputStream
                     byte buffer[];
                     buffer = new byte[1024];
                     //Read is synchronous call which keeps on waiting until data is available
                     int bytes = inStream.read(buffer);
                     message = new String(buffer, 0, bytes);
                     Log.i("", message);
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                            // view.append(message);
                         }
                     });

                 } catch (IOException e) {
                     break;
                 }
             }

         }
     };
        new Thread(runnable).start();
*/
    }/*to send bytes of data to bluetooth
    public void send(View v) {
       // String message = text.getText().toString();
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }*/

    public void mic(View v){
        promptSpeechInput();

    }
    public void fanon(View v) {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();
        String message="1";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Fanon.setVisibility(View.INVISIBLE);
            Fanoff.setVisibility(View.VISIBLE);
            editor.putString(fan,"off");
            editor.commit();
            createNotification("Fan switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanoff(View v) {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        String message="2";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Fanon.setVisibility(View.VISIBLE);
            Fanoff.setVisibility(View.INVISIBLE);
            editor.putString(fan,"on");
            editor.commit();
            createNotification("Fan switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingon(View v) {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        String message="3";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledceilingon.setVisibility(View.INVISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            editor.putString(ledceiling,"off");
            editor.commit();
            createNotification("Ceiling Lights switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingoff(View v) {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        String message="4";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledceilingon.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.INVISIBLE);
            editor.putString(ledceiling,"on");
            editor.commit();
            createNotification("Ceiling Lights switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodon(View v) {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        String message="5";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledwoodon.setVisibility(View.INVISIBLE);
            Ledwoodoff.setVisibility(View.VISIBLE);
            editor.putString(ledwood,"off");
            editor.commit();
            createNotification("Ceiling Led switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodoff(View v) {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        String message="6";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledwoodon.setVisibility(View.VISIBLE);
            Ledwoodoff.setVisibility(View.INVISIBLE);
            editor.putString(ledwood,"on");
            editor.commit();
            createNotification("Ceiling Led switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripon(View v) {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        String message="7";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledstripon.setVisibility(View.INVISIBLE);
            LedStripoff.setVisibility(View.VISIBLE);
            editor.putString(ledstrip,"off");
            editor.commit();
            createNotification("Led switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripoff(View v) {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        String message="8";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledstripon.setVisibility(View.VISIBLE);
            LedStripoff.setVisibility(View.INVISIBLE);
            editor.putString(ledstrip,"on");
            editor.commit();
            createNotification("Led switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanon() {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();
        String message="1";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Fanon.setVisibility(View.INVISIBLE);
            Fanoff.setVisibility(View.VISIBLE);
            editor.putString(fan,"off");
            editor.commit();
            createNotification("Fan switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanoff() {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        String message="2";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Fanon.setVisibility(View.VISIBLE);
            Fanoff.setVisibility(View.INVISIBLE);
            editor.putString(fan,"on");
            editor.commit();
            createNotification("Fan switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingon() {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        String message="3";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledceilingon.setVisibility(View.INVISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            editor.putString(ledceiling,"off");
            editor.commit();
            createNotification("Ceiling lights switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingoff() {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        String message="4";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledceilingon.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.INVISIBLE);
            editor.putString(ledceiling,"on");
            editor.commit();
            createNotification("Ceiling lights switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodon() {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        String message="5";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledwoodon.setVisibility(View.INVISIBLE);
            Ledwoodoff.setVisibility(View.VISIBLE);
            editor.putString(ledwood,"off");
            editor.commit();
            createNotification("Ceiling led switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodoff() {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        String message="6";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledwoodon.setVisibility(View.VISIBLE);
            Ledwoodoff.setVisibility(View.INVISIBLE);
            editor.putString(ledwood,"on");
            editor.commit();
            createNotification("Ceiling led switched on!","Via bluetooth.");


        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripon() {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        String message="7";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledstripon.setVisibility(View.INVISIBLE);
            LedStripoff.setVisibility(View.VISIBLE);
            editor.putString(ledstrip,"off");
            editor.commit();
            createNotification("Led switched off!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripoff() {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        String message="8";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Ledstripon.setVisibility(View.VISIBLE);
            LedStripoff.setVisibility(View.INVISIBLE);
            editor.putString(ledstrip,"on");
            editor.commit();
            createNotification("Led switched on!","Via bluetooth.");

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here
        //super.onBackPressed();
        try {
            btsocket.close();
            super.onBackPressed();
            this.finish();
        }catch(IOException e){
            Log.e("notclosed","btsocket");
        }
    }





    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speech = result.get(0);
                    Log.d("Speech result",speech);
                    if(speech.equals("fan on"))
                    {
                        fanoff();
                    }
                    if(speech.equals("fan of"))
                    {
                        fanon();
                    }
                    if(speech.equals("ceiling light on"))
                    {
                        ledceilingoff();
                    }
                    if(speech.equals("ceiling light off"))
                    {
                        ledceilingon();
                    }
                    if(speech.equals("wooden light on"))
                    {
                        ledwoodoff();;
                    }
                    if(speech.equals("everything off"))
                    {
                        fanon();
                        ledceilingon();
                        ledwoodon();
                        ledstripon();
                    }
                    if(speech.equals("everything on"))
                    {
                        fanoff();
                        ledceilingoff();
                        ledwoodoff();
                        ledstripoff();
                    }
                    if(speech.equals("wooden light off"))
                    {
                        ledwoodon();
                    }
                    if(speech.equals("LED off"))
                    {
                        ledstripon();
                    }
                    if(speech.equals("LED on"))
                    {
                        ledstripoff();
                    }
                    break;

                }
            }
        }
    }

    private void createNotification( String title, String mssg) {

        try {

            // JSONObject jsonObject = new JSONObject(messageBody);
            String msg =title;//jsonObject.getString("message");
            String timestamp = mssg;//jsonObject.getString("timestamp");
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

        }catch (Exception e)//JSONException e)
        {
            Log.d("JSON Exception",e.toString());
        }


    }
}
