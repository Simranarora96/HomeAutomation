package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ListView listView1 ;
    ArrayList<String> messagelist;
    ArrayList<Item> messages=new ArrayList<>();

    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView1 = (ListView) findViewById(R.id.simplelistView);

        messagelist  = db.getAllContacts();
        if(messagelist.size() != 0) {
            for (int i = messagelist.size()-1; i >=0; i--) {
                try {
                    JSONObject jobj = new JSONObject(messagelist.get(i));
                    if (jobj.getString("message").equals("Door Unlocked")) {
                        messages.add(new Item(jobj.getString("message"), jobj.getString("timestamp"), R.drawable.unlock));
                    }
                    if (jobj.getString("message").equals("Door Locked")) {
                        messages.add(new Item(jobj.getString("message"), jobj.getString("timestamp"), R.drawable.lock));
                    }
                    }catch(JSONException e)
                {
                    Log.d("JsonException",e.toString());
                }
           }


        }
        else{
listView1.setVisibility(View.INVISIBLE);

        }
        MyAdapter myAdapter=new MyAdapter(this,R.layout.list_view_item,messages);
        listView1.setAdapter(myAdapter);
    }
}