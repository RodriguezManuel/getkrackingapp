package com.example.getkracking.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("review")
    @Expose
    private String review;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewModel() {
    }

    /**
     *
     * @param score
     * @param review
     */
    public ReviewModel(int score, String review) {
        super();
        this.score = score;
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
