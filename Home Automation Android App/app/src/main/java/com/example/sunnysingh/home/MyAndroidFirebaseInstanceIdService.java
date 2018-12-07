package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */


import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";
    final String serverUri = "tcp://test.mosquitto.org:1883";
    final String clientid = "homeautomation";
    final String publishtopic = "esp8266_arduino";
    PahoMqttClient pahoMqttClient;
    MqttAndroidClient mqttAndroidClient;
    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {

        pahoMqttClient = new PahoMqttClient();

        mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), serverUri, clientid);
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("myregistration_id",token);
            pahoMqttClient.publishMessage(mqttAndroidClient, jsonObject, publishtopic );

        }catch(JSONException e)
        {
            e.printStackTrace();
        }catch(MqttException e)
        {
            e.printStackTrace();
        }catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
}