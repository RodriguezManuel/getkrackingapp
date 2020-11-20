package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.util.StringUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.getkracking.app.MyApplication;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //busco data por si viene de un link
        Intent intent = getIntent();
        Uri data = intent.getData();

        new Handler().postDelayed(() -> {
            //Manejo de si viene por link
            Intent nextIntent;
            if(true)
                nextIntent = new Intent(this, WelcomeActivity.class);
            else nextIntent = new Intent(this, HomeActivity.class);

            if(data != null && isNumeric(data.getLastPathSegment())){
                // si hay argumento y es numerico, lo agrego
                nextIntent.putExtra("routineId", data.getLastPathSegment());
            }

            startActivity(nextIntent);
                Intent homeIntent;
                //homeIntent = new Intent(this, HomeActivity.class);
                homeIntent = new Intent(this, WelcomeActivity.class);
            startActivity(homeIntent);
            finish();   //para que no pueda volver a esta pantalla
        }, SPLASH_TIME_OUT);
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            Log.d("IU", "ERROR EN PATH RECIBIDO, FALTA NUMERO DE RUTINA A ACCEDER\n");
            return false;
        }
    }

    public int attemptLogin( MyApplication application){
        String username_string = application.getPreferences().getUsername();
        String password_string = application.getPreferences().getPassword();
        application.getUserRepository().login(username_string, password_string)
                .observe(this, resource -> {
                    switch (resource.status) {
                        case LOADING:
                            Log.d("UI", "awaiting");
                            break;
                        case SUCCESS:
                            application.getPreferences().setAuthToken(resource.data);
                            application.getPreferences().setUsername(username_string);
                            application.getPreferences().setPassword(password_string);
                            Log.d("UI", "ALL GOOD :) -- token = " + application.getPreferences().getAuthToken());
                            Intent homeIntent = new Intent(this, HomeActivity.class);
                            startActivity(homeIntent);
                            this.finish();

                            break;
                        case ERROR:
                            Log.d("UI", "Error");
                            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
                            startActivity(welcomeIntent);
                            this.finish();
                            break;
                    }
                });
    }
}