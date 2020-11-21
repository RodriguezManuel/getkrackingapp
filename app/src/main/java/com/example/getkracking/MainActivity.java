package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.util.StringUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.getkracking.app.MyApplication;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.UserViewModel;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, ((MyApplication) this.getApplication()).getUserRepository());
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        //busco data por si viene de un link
        Intent intent = getIntent();
        Uri data = intent.getData();

        new Handler().postDelayed(() -> {
            attemptLogin(data);
        }, SPLASH_TIME_OUT);
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            Log.d("IU", "ERROR EN PATH RECIBIDO, FALTA NUMERO DE RUTINA A ACCEDER\n");
            return false;
        }
    }

<<<<<<< HEAD
    public void attemptLogin( MyApplication application){
=======
    public void attemptLogin(Uri data) {
        MyApplication application = (MyApplication) getApplication();
>>>>>>> main
        String username_string = application.getPreferences().getUsername();
        String password_string = application.getPreferences().getPassword();
        userViewModel.login(username_string, password_string)
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
                                    if (data != null && isNumeric(data.getLastPathSegment())) {
                                        // si hay argumento y es numerico, lo agrego
                                        homeIntent.putExtra("routineId", data.getLastPathSegment());
                                    }
                                    startActivity(homeIntent);
                                    this.finish();
                                    break;

                                case ERROR:
                                    //lo mando a loguearse
                                    Log.d("UI", "Error");
                                    Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
                                    if (data != null && isNumeric(data.getLastPathSegment())) {
                                        // si hay argumento y es numerico, lo agrego
                                        welcomeIntent.putExtra("routineId", data.getLastPathSegment());
                                    }
                                    startActivity(welcomeIntent);
                                    this.finish();
                                    break;
                            }
                        }
                );
    }

}