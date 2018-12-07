package com.example.sunnysingh.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sunny Singh on 2/4/2018.
 */

public class Pubclient extends AppCompatActivity {
    RelativeLayout Tvon;
    RelativeLayout Tvoff;
    RelativeLayout Settopboxon;
    RelativeLayout Settopboxoff;
    RelativeLayout Switchon;
    RelativeLayout Switchoff;
    RelativeLayout Fanon;
    RelativeLayout Fanoff;
    RelativeLayout Ledceilingon;
    RelativeLayout Ledceilingoff;
    RelativeLayout Ledwoodon;
    RelativeLayout Ledwoodoff;
    RelativeLayout Ledstripon;
    RelativeLayout LedStripoff;
    SharedPreferences sharedpreferences;
    public static final String Mypreferencestv="Mypreftv";
    public static final String Mypreferencesset="Myprefset";
    public static final String Mypreferencessw="Myprefsw";
    public static final String tv="";
    public static final String settopbox="";
    public static final String powerswitch="";
    public static final String Mypreferencesfan="Myprefsfan";
    public static final String Mypreferencesledceiling="Myprefsledceiling";
    public static final String Mypreferencesledwood="Myprefledwood";
    public static final String Mypreferencesledstrip="Myprefledstrip";
    public static final String fan="";
    public static final String ledceiling="";
    public static final String ledwood="";
    public static final String ledstrip="";
    public MqttAndroidClient mqttAndroidClient;
    PahoMqttClient pahoMqttClient;
    final String serverUri = "tcp://test.mosquitto.org:1883";
    final String clientid = "homeautomation";
    final String topic = "esp8266_arduino";
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String baddr = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        baddr = intent.getStringExtra("Baddr");
        pahoMqttClient = new PahoMqttClient();

        mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), serverUri, clientid);


        if (baddr.equals("98:D3:31:90:86:16"))
        {
            setContentView(R.layout.communication_view);
            Log.d("Publishing for","Master Bedroom");
            Log.d("tv",tv);
            Log.d("settopbox",settopbox);
            Log.d("switch",powerswitch);
            SharedPreferences prefstv = getSharedPreferences(Mypreferencestv, MODE_PRIVATE);
            SharedPreferences prefsset = getSharedPreferences(Mypreferencesset, MODE_PRIVATE);
            SharedPreferences prefssw = getSharedPreferences(Mypreferencessw, MODE_PRIVATE);

            String tvstatus = prefstv.getString(tv, null);


            String settopboxstatus = prefsset.getString(settopbox, null);
            String powerswitchstatus = prefssw.getString(powerswitch, null);
            Tvon = (RelativeLayout) findViewById(R.id.tvon);
            Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
            Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
            Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
            Switchon = (RelativeLayout) findViewById(R.id.switchon);
            Switchoff = (RelativeLayout) findViewById(R.id.switchoff);


            if(tvstatus!=null && settopboxstatus!=null && powerswitchstatus!=null) {

                if (tvstatus.equals("on")) {
                    Tvon.setVisibility(View.VISIBLE);
                    Tvoff.setVisibility(View.INVISIBLE);
                }
                if (tvstatus.equals("off")) {
                    Tvon.setVisibility(View.INVISIBLE);
                    Tvoff.setVisibility(View.VISIBLE);
                }
                if (settopboxstatus.equals("on")) {
                    Settopboxon.setVisibility(View.VISIBLE);
                    Settopboxoff.setVisibility(View.INVISIBLE);
                }
                if (settopboxstatus.equals("off")) {
                    Settopboxon.setVisibility(View.INVISIBLE);
                    Settopboxoff.setVisibility(View.VISIBLE);
                }
                if (powerswitchstatus.equals("on")) {
                    Switchon.setVisibility(View.VISIBLE);
                    Switchoff.setVisibility(View.INVISIBLE);
                }
                if (powerswitchstatus.equals("off")) {
                    Switchon.setVisibility(View.INVISIBLE);
                    Switchoff.setVisibility(View.VISIBLE);
                }
            }

            else{
                Switchoff.setVisibility(View.VISIBLE);
                Settopboxoff.setVisibility(View.VISIBLE);
                Tvoff.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor1 = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
                SharedPreferences.Editor editor2 = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();
                SharedPreferences.Editor editor3= getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();
                editor1.putString(tv,"off");
                editor1.commit();
                editor2.putString(settopbox,"off");
                editor2.commit();
                editor3.putString(powerswitch,"off");
                editor3.commit();

            }


        }
        else
        {
            setContentView(R.layout.bedroom);
            Log.d("Publishing for","Bedroom");
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

            if(fanstatus!=null && ledceilingstatus!=null && ledwoodstatus!=null && ledstripstatus!=null) {
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

        }

    }

    public void mic(View v){
        promptSpeechInput();

    }
    public void tvon(View v) {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
        int message= 1;

                        try {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("baddr", baddr); // Set the first name/pair
    jsonObj.put("cmd", message);
    jsonObj.put("notify", "Television switched off!");

    try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Tvon.setVisibility(View.INVISIBLE);
            Tvoff.setVisibility(View.VISIBLE);
            editor.putString(tv,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.toString());
        }
    }
    public void tvoff(View v) {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        int message=2;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Television switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }

            Tvon.setVisibility(View.VISIBLE);
            Tvoff.setVisibility(View.INVISIBLE);
            editor.putString(tv,"on");
            editor.commit();

        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxon(View v) {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        int message=3;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Settop box switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Settopboxon.setVisibility(View.INVISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            editor.putString(settopbox,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxoff(View v) {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        int message=4;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Settop box switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }

            Settopboxon.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.INVISIBLE);
            editor.putString(settopbox,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchon(View v) {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        int message=5;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Power switch switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Switchon.setVisibility(View.INVISIBLE);
            Switchoff.setVisibility(View.VISIBLE);
            editor.putString(powerswitch,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchoff(View v) {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        int message=6;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Power switch switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Switchon.setVisibility(View.VISIBLE);
            Switchoff.setVisibility(View.INVISIBLE);
            editor.putString(powerswitch,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void tvon() {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
        int message=1;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Television switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Tvon.setVisibility(View.INVISIBLE);
            Tvoff.setVisibility(View.VISIBLE);
            editor.putString(tv,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void tvoff() {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        int message=2;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Television switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Tvon.setVisibility(View.VISIBLE);
            Tvoff.setVisibility(View.INVISIBLE);
            editor.putString(tv,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxon() {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        int message=3;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Settop box switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Settopboxon.setVisibility(View.INVISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            editor.putString(settopbox,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxoff() {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        int message=4;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Settop box switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Settopboxon.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.INVISIBLE);
            editor.putString(settopbox,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchon() {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        int message=5;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Power switch switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Switchon.setVisibility(View.INVISIBLE);
            Switchoff.setVisibility(View.VISIBLE);
            editor.putString(powerswitch,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchoff() {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        int message=6;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Power switch switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Switchon.setVisibility(View.VISIBLE);
            Switchoff.setVisibility(View.INVISIBLE);
            editor.putString(powerswitch,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here
        //super.onBackPressed();
        try {
            pahoMqttClient.disconnect(mqttAndroidClient);
            super.onBackPressed();
            this.finish();
        }catch(MqttException e){
            Log.e("notclosed",e.toString());
        }
    }


    public void fanon(View v) {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();
        int message=1;


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Fan switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Fanon.setVisibility(View.INVISIBLE);
            Fanoff.setVisibility(View.VISIBLE);
            editor.putString(fan,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanoff(View v) {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        int message=2;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Fan switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Fanon.setVisibility(View.VISIBLE);
            Fanoff.setVisibility(View.INVISIBLE);
            editor.putString(fan,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingon(View v) {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        int message=3;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling Lights switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledceilingon.setVisibility(View.INVISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            editor.putString(ledceiling,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingoff(View v) {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        int message=4;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling Lights switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledceilingon.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.INVISIBLE);
            editor.putString(ledceiling,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodon(View v) {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        int message=5;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling led switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Ledwoodon.setVisibility(View.INVISIBLE);
            Ledwoodoff.setVisibility(View.VISIBLE);
            editor.putString(ledwood,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodoff(View v) {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        int message=6;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling led switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledwoodon.setVisibility(View.VISIBLE);
            Ledwoodoff.setVisibility(View.INVISIBLE);
            editor.putString(ledwood,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripon(View v) {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        int message=7;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Led switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledstripon.setVisibility(View.INVISIBLE);
            LedStripoff.setVisibility(View.VISIBLE);
            editor.putString(ledstrip,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripoff(View v) {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        int message=8;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Led switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledstripon.setVisibility(View.VISIBLE);
            LedStripoff.setVisibility(View.INVISIBLE);
            editor.putString(ledstrip,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanon() {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();
        int message=1;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Fan switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Fanon.setVisibility(View.INVISIBLE);
            Fanoff.setVisibility(View.VISIBLE);
            editor.putString(fan,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void fanoff() {
        Fanon = (RelativeLayout) findViewById(R.id.fanon);
        Fanoff = (RelativeLayout) findViewById(R.id.fanoff);
        int message=2;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesfan, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Fan switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Fanon.setVisibility(View.VISIBLE);
            Fanoff.setVisibility(View.INVISIBLE);
            editor.putString(fan,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingon() {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        int message=3;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling led switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Ledceilingon.setVisibility(View.INVISIBLE);
            Ledceilingoff.setVisibility(View.VISIBLE);
            editor.putString(ledceiling,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledceilingoff() {
        Ledceilingon = (RelativeLayout) findViewById(R.id.ledpopon);
        Ledceilingoff = (RelativeLayout) findViewById(R.id.ledpopoff);
        int message=4;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledceiling, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling led switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledceilingon.setVisibility(View.VISIBLE);
            Ledceilingoff.setVisibility(View.INVISIBLE);
            editor.putString(ledceiling,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodon() {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        int message=5;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling lights switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledwoodon.setVisibility(View.INVISIBLE);
            Ledwoodoff.setVisibility(View.VISIBLE);
            editor.putString(ledwood,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledwoodoff() {
        Ledwoodon = (RelativeLayout) findViewById(R.id.ledwoodon);
        Ledwoodoff = (RelativeLayout) findViewById(R.id.ledwoodoff);
        int message=6;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledwood, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Ceiling lights switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            Ledwoodon.setVisibility(View.VISIBLE);
            Ledwoodoff.setVisibility(View.INVISIBLE);
            editor.putString(ledwood,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripon() {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        int message=7;

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Led switched off!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledstripon.setVisibility(View.INVISIBLE);
            LedStripoff.setVisibility(View.VISIBLE);
            editor.putString(ledstrip,"off");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void ledstripoff() {
        Ledstripon = (RelativeLayout) findViewById(R.id.ledstripon);
        LedStripoff = (RelativeLayout) findViewById(R.id.ledstripoff);
        int message=8;
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesledstrip, MODE_PRIVATE).edit();


        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("baddr", baddr); // Set the first name/pair
            jsonObj.put("cmd", message);
            jsonObj.put("notify", "Led switched on!");

            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, jsonObj, topic );
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }            Ledstripon.setVisibility(View.VISIBLE);
            LedStripoff.setVisibility(View.INVISIBLE);
            editor.putString(ledstrip,"on");
            editor.commit();


        } catch (JSONException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
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

                    if(speech.equals("TV on"))
                    {
                        tvoff();
                    }
                    if(speech.equals("TV off"))
                    {
                        tvon();
                    }
                    if(speech.equals("Airtel on"))
                    {
                        settopboxoff();
                    }
                    if(speech.equals("Airtel of"))
                    {
                        settopboxon();
                    }
                    if(speech.equals("power switch on"))
                    {
                        switchoff();
                    }
                    if(speech.equals("all of"))
                    {
                        switchon();
                        settopboxon();
                        tvon();
                    }
                    if(speech.equals("all on"))
                    {
                        switchoff();
                        settopboxoff();
                        tvoff();
                    }
                    if(speech.equals("power switch off"))
                    {
                        switchon();
                    }
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



}
