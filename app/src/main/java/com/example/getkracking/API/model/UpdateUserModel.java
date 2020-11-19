//-----------------------------------com.example.getkracking.API.model.User.java-----------------------------------

package com.example.getkracking.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserModel {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private Long birthdate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateUserModel() {
    }


    /**
     * @param username
     * @param fullName
     * @param gender
     * @param birthdate
     * @param email
     * @param phone
     * @param avatarUrl
     * */
    public UpdateUserModel( String username, String fullName, String gender, Long birthdate, String email, String phone, String avatarUrl) {
        super();
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }
}