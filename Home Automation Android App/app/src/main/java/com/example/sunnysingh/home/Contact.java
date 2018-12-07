package com.example.sunnysingh.home;

/**
 * Created by Sunny Singh on 2/6/2018.
 */

public class Contact {
        
        String _message;
        String time_stamp;

        // Empty constructor
        public Contact(){

        }


        // constructor
        public Contact(String message, String time_stamp){
            this._message = message;
            this.time_stamp = time_stamp;
        }
        // getting ID


        // getting message
        public String get_message(){
            return this._message;
        }

        // setting message
        public void set_message(String message){
            this._message = message;
        }

        // getting phone number
        public String getTime_stamp(){
            return this.time_stamp;
        }

        // setting phone number
        public void setTime_stamp(String time_stamp){
            this.time_stamp = time_stamp;
        }
}

