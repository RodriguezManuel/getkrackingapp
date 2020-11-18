package com.example.getkracking.entities;

import java.util.ArrayList;

public class CycleVO {

    private String name;
    private ArrayList<ExerciseVO> exercises;
    private int order;
    private int id;

    public CycleVO(int id, String name, ArrayList<ExerciseVO> exercises , int order) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExercises(ArrayList<ExerciseVO> exercises) {
        this.exercises = exercises;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ExerciseVO> getExercises() {
        return exercises;
    }
}
