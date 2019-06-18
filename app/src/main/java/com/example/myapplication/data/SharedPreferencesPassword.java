package com.example.myapplication.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesPassword{

    private String MainPassword = "";

    private SharedPreferences settings;
    SharedPreferences.Editor editor;

    public SharedPreferencesPassword(Context context) {
        settings =  context.getSharedPreferences("FileName", Activity.MODE_PRIVATE);
        editor = settings.edit();
    }

    public void setSharedPreferencesPassword(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPreferencesPassword(String key){
        return settings.getString(key,"");
    }

    public void removeSharedPreferencesPassword(String key){
        editor.remove(key);
        editor.apply();
    }

}
