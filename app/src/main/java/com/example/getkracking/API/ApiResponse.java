package com.example.getkracking.API;

import com.example.getkracking.API.model.MyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {
    private T data;
    private MyError error;

    public T getData(){
        return data;
    }

    public MyError getError(){
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
            parseError(e.getMessage());
        }
        if ( message != null && message.trim().length() > 0 ){
            Gson gson = new Gson();
            //gson.fromJson(message , new TypeToken<Error>() {}.getType());
            this.error =  gson.fromJson(message, new TypeToken<MyError>() {}.getType());
        }
        if ( error == null ) {
            List<String> details = new ArrayList<>();
            details.add("Null error");
            error = new MyError(MyError.LOCAL_UNEXPECTED_ERROR, "Unkown error", details );
        }
    }

    private void parseError(String message){
        List<String>  details = null;
        if ( message != null){
            //1:16:25
            this.error = new MyError(MyError.LOCAL_UNEXPECTED_ERROR , "Unknown error" , details);
            details = new ArrayList<String>();
            details.add(message);
        }else {
            this.error = new MyError(MyError.LOCAL_UNEXPECTED_ERROR, "Unexpected error", details);
        }
    }
}
