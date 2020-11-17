//-----------------------------------com.example.getkracking.API.model.Token.java-----------------------------------

package com.example.getkracking.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     *
     */
    public TokenModel() {
    }

    /**
     *
     * @param token
     */
    public TokenModel(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}