package com.example.getkracking.API;


import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final AppPreferences preferences;
    public AuthInterceptor(Context context){
        this.preferences = new AppPreferences(context);
    }
    @Override
    public Response intercept(Interceptor.Chain chain ) throws IOException{
        Request.Builder request = chain.request().newBuilder();
        if(preferences.getToken() != null){
            request.addHeader("Authorization", "Bearer " + preferences.getToken() );
        }
        return chain.proceed(request.build());
    }
}
