package com.example.getkracking.API;


import android.content.Context;

import com.example.getkracking.app.MyApplication;
import com.example.getkracking.app.MyPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final MyPreferences preferences;
    public AuthInterceptor(Context context){
        this.preferences = new MyPreferences(context);
    }
    @Override
    public Response intercept(Interceptor.Chain chain ) throws IOException{
        Request.Builder request = chain.request().newBuilder();
        if(preferences.getAuthToken() != null){
            System.out.println("EN AUTHINTERCEPTOR");
            request.addHeader("Authorization", "Bearer " + preferences.getAuthToken() );
            System.out.println("Salí de la zona crítica");
        }
        return chain.proceed(request.build());
    }
}
