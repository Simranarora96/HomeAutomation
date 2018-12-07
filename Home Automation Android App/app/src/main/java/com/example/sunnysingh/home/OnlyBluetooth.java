package com.example.sunnysingh.home;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Sunny Singh on 2/9/2018.
 */

public class OnlyBluetooth extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT =1;
    List<String> dname = new ArrayList<String>();
    List<String> mac = new ArrayList<String>();
    BluetoothSocket btSocket;
    BluetoothDevice device;
    BluetoothAdapter mBluetoothAdapter;
    String address = "";
    TextView mydevice;
    TextView bedroom;
    Context context;
    int BLUETOOTH_REQUEST = 1;
    String MasterBedroomBaddr = "98:D3:31:90:86:16";
    String BedroomBaddr = "00:18:E4:0A:00:01";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    Intent mServiceIntent;
    private PeriodicService mSensorService;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble);
        mydevice = (TextView) findViewById(R.id.device);
        bedroom = (TextView) findViewById(R.id.device2);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        context = getBaseContext();
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        mydevice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isEnabled())
                {
                    address = "98:D3:31:90:86:16";
                    Log.i("cool:",address);
                    device = mBluetoothAdapter.getRemoteDevice(address);
                    try {
                        btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                    }
                    catch (IOException e)
                    {
                        Toast.makeText(getBaseContext(),"Reset your device. ",Toast.LENGTH_LONG).show();
                    }
                    mBluetoothAdapter.cancelDiscovery();
                    try {
                        btSocket.connect();
                        Toast.makeText(getBaseContext(),"Connected to Master Bedroom.",Toast.LENGTH_LONG).show();
                        MainActivity.BTSocket.setSocket(btSocket);
                        Intent i = new Intent(OnlyBluetooth.this,CommunicationView.class);
                        startActivity(i);
                    }
                    catch (IOException e1)
                    {

                        Toast.makeText(getBaseContext(),"Your device seems to be offline.", Toast.LENGTH_LONG).show();

                    }


                }
                if(!mBluetoothAdapter.isEnabled())
                {

                    Toast.makeText(getBaseContext(),"Turn on your bluetooth.",Toast.LENGTH_LONG).show();

                }

            }
        });

        bedroom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isEnabled()) {
                    address = "00:18:E4:0A:00:01";
                    Log.i("coolbedroom:",address);
                    device = mBluetoothAdapter.getRemoteDevice(address);
                    try {
                        btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                    }
                    catch (IOException e)
                    {
                        Toast.makeText(getBaseContext(),"Reset your device. ",Toast.LENGTH_LONG).show();
                    }
                    mBluetoothAdapter.cancelDiscovery();
                    try {
                        btSocket.connect();
                        Toast.makeText(getBaseContext(),"Connected to Bedroom.",Toast.LENGTH_LONG).show();
                        MainActivity.BTSocket.setSocket(btSocket);
                        Intent i = new Intent(OnlyBluetooth.this,Bedroom.class);
                        startActivity(i);
                    }
                    catch (IOException e1)
                    {
                        Toast.makeText(getBaseContext(),"Your device seems to be offline.", Toast.LENGTH_LONG).show();

                    }

                    if(!mBluetoothAdapter.isEnabled())
                    {

                        Toast.makeText(getBaseContext(),"Turn on your bluetooth.",Toast.LENGTH_LONG).show();

                    }

                }

            }
        });





    }




    public static class BTSocket{

        private static BluetoothSocket socket;
        public  static synchronized BluetoothSocket getSocket(){
            return socket;
        }
        public static synchronized void setSocket(BluetoothSocket socket1){
            socket = socket1;
        }
    }


}

