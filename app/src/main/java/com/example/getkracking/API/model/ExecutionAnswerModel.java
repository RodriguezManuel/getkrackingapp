package com.example.getkracking.API.model;

import com.example.getkracking.entities.RoutineVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExecutionAnswerModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("wasModified")
    @Expose
    private boolean wasModified;
    @SerializedName("routine")
    @Expose
    private RoutineModel routine;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExecutionAnswerModel() {
    }

    /**
     *
     * @param date
     * @param duration
     * @param routine
     * @param wasModified
     * @param id
     */
    public ExecutionAnswerModel(int id, int date, int duration, boolean wasModified, RoutineModel routine) {
        super();
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    public RoutineModel getRoutine() {
        return routine;
    }

    public void setRoutine(RoutineModel routine) {
        this.routine = routine;
    }

}