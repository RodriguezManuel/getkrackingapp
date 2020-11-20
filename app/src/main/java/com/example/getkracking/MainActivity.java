package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.util.StringUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
}