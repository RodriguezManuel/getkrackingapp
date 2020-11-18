package com.example.getkracking.API;

import android.util.Log;

import com.example.getkracking.API.model.MyErrorModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {
    private T data;
    private MyErrorModel error;

    public T getData(){
        return data;
    }

    public MyErrorModel getError(){
        return error;
    }

    public ApiResponse( Response<T> response){
        parseResponse(response);
    }

    public ApiResponse(Throwable t) {
        parseError(t.getMessage());
    }

    private void parseResponse(Response<T> response){
        if( response.isSuccessful()){
            data = response.body();
            return;
        }
        if(response.errorBody() == null){
            parseError("Missing error body");
            return;
        }
        String message = null;
        try{
            message=response.errorBody().string();
        }catch (IOException e){
            Log.e("api" , e.toString());
            parseError(e.getMessage());
            return;
        }
        if ( message != null && message.trim().length() > 0 ){
            Gson gson = new Gson();
            //gson.fromJson(message , new TypeToken<Error>() {}.getType());
            this.error =  gson.fromJson(message, new TypeToken<MyErrorModel>() {}.getType());
        }
    }

    private void parseError(String message){
        List<String>  details = null;
        if ( message != null){
            //1:16:25
            details = new ArrayList<String>();
            details.add(message);
            this.error = new MyErrorModel(MyErrorModel.LOCAL_UNEXPECTED_ERROR , "Unknown error" , details);

        }else {
            this.error = new MyErrorModel(MyErrorModel.LOCAL_UNEXPECTED_ERROR, "Unexpected error", details);
        }
    }
}
