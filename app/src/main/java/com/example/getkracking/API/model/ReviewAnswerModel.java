package com.example.getkracking.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewAnswerModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("routine")
    @Expose
    private RoutineModel routine;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewAnswerModel() {
    }

    /**
     *
     * @param date
     * @param score
     * @param routine
     * @param review
     * @param id
     */
    public ReviewAnswerModel(int id, int date, int score, String review, RoutineModel routine) {
        super();
        this.id = id;
        this.date = date;
        this.score = score;
        this.review = review;
        this.routine = routine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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

    public RoutineModel getRoutine() {
        return routine;
    }

    public void setRoutine(RoutineModel routine) {
        this.routine = routine;
    }

}