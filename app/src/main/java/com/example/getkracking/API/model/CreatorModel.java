package com.example.getkracking.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatorModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("dateCreated")
    @Expose
    private Long dateCreated;
    @SerializedName("dateLastActive")
    @Expose
    private Long dateLastActive;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreatorModel() {
    }

    /**
     *
     * @param dateLastActive
     * @param dateCreated
     * @param gender
     * @param avatarUrl
     * @param id
     * @param username
     */
    public CreatorModel(int id, String username, String gender, String avatarUrl, Long dateCreated, Long dateLastActive) {
        super();
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Long dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

}