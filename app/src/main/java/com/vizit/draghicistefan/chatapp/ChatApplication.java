package com.vizit.draghicistefan.chatapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import model.Message;

/**
 * Created by Draghici Stefan on 04.02.2016.
 */
public class ChatApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this);
    }
}
