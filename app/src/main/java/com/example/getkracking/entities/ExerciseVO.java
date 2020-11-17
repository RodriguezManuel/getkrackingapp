package com.example.getkracking.entities;

public class ExerciseVO {

    private String name;
    private int duration, quantity;

    public ExerciseVO(String name, int quantity, int duration) {
        this.name = name;
        this.quantity = quantity;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

}
