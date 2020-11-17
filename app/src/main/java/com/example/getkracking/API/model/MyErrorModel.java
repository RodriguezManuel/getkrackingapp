package com.example.getkracking.API.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyErrorModel {
    public static final int LOCAL_UNEXPECTED_ERROR = 10;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("details")
    @Expose
    private List<String> details = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public MyErrorModel() {
    }

    /**
     *
     * @param code
     * @param description
     * @param details
     */
    public MyErrorModel(int code, String description, List<String> details) {
        super();
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

}