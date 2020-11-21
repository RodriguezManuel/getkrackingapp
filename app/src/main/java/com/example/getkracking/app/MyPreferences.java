package com.example.getkracking.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.utils.Constants;


public class MyPreferences {
    private final String AUTH_TOKEN = "auth_token";
    private final SharedPreferences sharedPreferences;
    private final String USERNAME = "username" ;
    private final String PASSWORD = "password" ;
    private final String LOGGED = "logged";
    public void setUsername(String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }
    public void setPassword(String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }
    public void setLogged(boolean logged){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED , logged);
        editor.apply();
    }


    public boolean getLogged(){ return sharedPreferences.getBoolean(LOGGED , false); }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

    public String getPassword(){
        return sharedPreferences.getString(PASSWORD , null );
    }

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }
}
