package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HomeMessages";

    // Contacts table name
    private static final String TABLE_CONTACTS = "messages";

    // Contacts Table Columns names
    private static final String KEY_Message = "message";
    private static final String KEY_TIME_STAMP = "time_stamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("+
                KEY_Message + " TEXT,"
                + KEY_TIME_STAMP + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addmessage(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Message, contact.get_message()); // Contact Name
        values.put(KEY_TIME_STAMP, contact.getTime_stamp()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public ArrayList<String> getAllContacts() {
        ArrayList<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{

                    JSONObject object = new JSONObject();
                    object.put("message", cursor.getString(0));
                    object.put("timestamp", cursor.getString(1));

                    // Adding contact to list
                    list.add(object.toString());
}catch(JSONException e)
                    {
                        Log.d("JsonException", e.toString());
                    }
            } while (cursor.moveToNext());
        }

        // return contact list
      //  System.out.print("DATA"+contactList.get(3));
        db.close();
        return list;
    }





}