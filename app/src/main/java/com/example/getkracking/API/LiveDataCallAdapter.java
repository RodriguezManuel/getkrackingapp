package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;


import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R , LiveData<ApiResponse<R>>> {
    private final Type responseType;
    @Override
    public Type responseType(){
        return responseType;
    }
    public LiveDataCallAdapter(Type responseType){
        this.responseType = responseType;
    }


    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            final AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive(){ //llamada a retrofit
                super.onActive();
                if( started.compareAndSet(false , true)){
                    //toda la logica aca
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(new ApiResponse<>(t));
                        }
                    });
                }
            }

        };
    }
}
