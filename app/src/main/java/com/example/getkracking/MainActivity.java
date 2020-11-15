package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(this, HomeActivity.class);

            //Intent homeIntent = new Intent(this, WelcomeActivity.class);

            startActivity(homeIntent);
            finish();   //para que no pueda volver a esta pantalla
        }, SPLASH_TIME_OUT);
    }
}