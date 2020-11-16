package com.example.getkracking.API;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.getkracking.R;

public class AppPreferences {
    public final String AUTH_TOKEN = "auth_token";
    private SharedPreferences sharedPreferences;
    public AppPreferences(Context context){
       sharedPreferences =  context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }
    public void setAuthToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN , token);
        editor.apply();
    }
    public String getToken(){
        return sharedPreferences.getString(AUTH_TOKEN , null);
    }
}
