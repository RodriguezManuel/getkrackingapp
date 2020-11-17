package com.example.getkracking.app;

import android.app.Application;

import androidx.room.Room;

import com.example.getkracking.API.ApiClient;
import com.example.getkracking.API.ApiSportService;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.repository.AppExecutors;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.utils.Constants;


public class MyApplication extends Application {

    AppExecutors appExecutors;
    MyPreferences preferences;
    UserRepository userRepository;

    public MyPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new MyPreferences(this);

        appExecutors = new AppExecutors();

        ApiUserService userService = ApiClient.create(this, ApiUserService.class);
        ApiSportService sportService = ApiClient.create(this, ApiSportService.class);

        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, Constants.DATABASE_NAME).build();

        userRepository = new UserRepository(appExecutors, userService, database);

    }
}