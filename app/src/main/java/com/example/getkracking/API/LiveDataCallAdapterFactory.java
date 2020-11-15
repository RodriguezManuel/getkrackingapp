package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.RecursiveTask;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory  extends CallAdapter.Factory {
    @Override
    public CallAdapter<? , ?> get(Type returnType , Annotation[] annotations , Retrofit retrofit){
        if ( getRawType(returnType) != LiveData.class){
            return null;
        }
        Type observervableType = getParameterUpperBound( 0 , (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observervableType);
        if ( rawObservableType != ApiResponse.class){
            throw new IllegalArgumentException("Type must be an api response");
        }
        if ( !(observervableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("api response must be parametrized");
        }
        Type bodyType = getParameterUpperBound( 0 , (ParameterizedType) observervableType);
        return new LiveDataCallAdapter<>(bodyType);

    }
}
