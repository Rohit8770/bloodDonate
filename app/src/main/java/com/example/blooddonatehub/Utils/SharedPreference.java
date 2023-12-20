package com.example.blooddonatehub.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {


    private static  final String SHARED_PREF_NAME = "MyAppPrefs";
    private static  final String KEY_IS_LOGGED_IN = "isLoggedin";
    private static final String ONBOARDING_COMPLETED = "onboarding_completed";

    private static final String KEY_USER_ID = "userId";



    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private Context context;

    public SharedPreference(Context context) {

        this.context = context;
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public Boolean getLoggedIn() {
        return sharedPreference.getBoolean(KEY_IS_LOGGED_IN,false);
    }


    public boolean isLoggedIn(){
        return  sharedPreference.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public void setStringvalue(String key,String value){
        editor.putString(key, value);
        editor.apply();
    }

    public  String getStringvalue(String key, String s){
        return  sharedPreference.getString(key,"");
    }


    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId).apply();
    }




}
