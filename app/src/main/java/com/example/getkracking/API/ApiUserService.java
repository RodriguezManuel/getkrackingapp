package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.API.model.RegisterCredentialsModel;
import com.example.getkracking.API.model.TokenModel;
import com.example.getkracking.API.model.UpdateUserModel;
import com.example.getkracking.API.model.UserModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface ApiUserService {

    @POST("user/login")
    LiveData<ApiResponse<TokenModel>> login(@Body CredentialsModel credentialsModel);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<UserModel>> getCurrent();

    @POST("user")
    LiveData<ApiResponse<UserModel>> register(@Body RegisterCredentialsModel registerCredentialsModel);

    @PUT("user/current")
    LiveData<ApiResponse<UserModel>> updateCurrent(@Body UpdateUserModel updateUserModel);

}
