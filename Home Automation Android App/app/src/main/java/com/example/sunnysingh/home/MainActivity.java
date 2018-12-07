package com.example.sunnysingh.home;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        mydevice = (TextView) findViewById(R.id.device);
        bedroom = (TextView) findViewById(R.id.device2);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        context = getBaseContext();
        Intent i = new Intent(context, PeriodicService.class);
        mSensorService = new PeriodicService(this);
        mServiceIntent = new Intent(this, mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected == FALSE) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }
        mydevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected == TRUE) {
                    Intent i = new Intent(MainActivity.this, Pubclient.class);
                    i.putExtra("Baddr", MasterBedroomBaddr);
                    startActivity(i);
                }
                if (isConnected == FALSE) {

                    Toast.makeText(getBaseContext(), "Currently offline!. Use bluetooth", Toast.LENGTH_LONG).show();

                }

            }
        });

        bedroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected == TRUE) {
                    Intent i = new Intent(MainActivity.this, Pubclient.class);
                    i.putExtra("Baddr", BedroomBaddr);
                    startActivity(i);
                }

                if (isConnected == FALSE) {

                    Toast.makeText(getBaseContext(), "Currently offline!. Use bluetooth", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

public void ble(View view)
    {
        Intent intent = new Intent(MainActivity.this, OnlyBluetooth.class);
        startActivity(intent);
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

    public void locklog(View v)
    {
        Intent i = new Intent(MainActivity.this,ResultActivity.class);
        startActivity(i);
    }
    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

}
