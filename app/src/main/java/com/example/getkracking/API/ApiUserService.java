package com.example.getkracking.API;

import android.media.session.MediaSession;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.Credentials;
import com.example.getkracking.API.model.RegisterCredentials;
import com.example.getkracking.API.model.Token;
import com.example.getkracking.API.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiUserService {

    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @POST("user")
    LiveData<ApiResponse<User>> register(@Body RegisterCredentials registerCredentials);


}
