package com.example.getkracking.API;

import android.content.Context;
import android.os.Build;

import com.example.getkracking.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final int CONNECT_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;

    private static final String BASE_URL = "http://192.168.0.10:8080/api/";

    private ApiClient(){

    }

    public static <S> S create(Context context, Class<S> service ) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().
            setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE );

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new AuthInterceptor(context))
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT , TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT , TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT , TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        return retrofit.create(service);
    }
}
