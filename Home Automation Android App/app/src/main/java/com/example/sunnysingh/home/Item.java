package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */

public class Item {

    String message;
    String timestamp;
    int icon;

    public Item(String message, String timestamp,int icon)
    {
        this.message=message;
        this.timestamp=timestamp;
        this.icon=icon;
    }
    public String getmessage()
    {
        return message;
    }
    public String gettimestamp()
    {
        return timestamp;
    }
    public int geticon()
    {
        return icon;
    }
}
