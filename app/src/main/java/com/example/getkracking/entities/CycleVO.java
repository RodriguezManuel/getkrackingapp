package com.example.getkracking.entities;

import java.util.ArrayList;

public class CycleVO {

    private String name;
    private ArrayList<ExerciseVO> exercises;

    public CycleVO(String name, ArrayList<ExerciseVO> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ExerciseVO> getExercises() {
        return exercises;
    }
}
