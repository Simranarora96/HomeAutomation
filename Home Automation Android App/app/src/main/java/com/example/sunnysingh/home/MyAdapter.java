package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> messages = new ArrayList<>();

    public MyAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        messages = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_item, null);
        TextView message = (TextView) v.findViewById(R.id.textdown);
        TextView timestamp = (TextView) v.findViewById(R.id.Key);
        ImageView imageView = (ImageView) v.findViewById(R.id.User);
        message.setText(messages.get(position).getmessage());
        timestamp.setText(messages.get(position).gettimestamp());
        imageView.setImageResource(messages.get(position).geticon());
        return v;

    }

}