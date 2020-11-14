package com.example.getkracking.classes;

//DEBERIA TRANSFORMARSE EN DAO

public class RoutineVO {
    private String name, description;
    private float levelCategory1, levelCategory2;
    private int duration;   // en minutos
    private boolean favorited;

    public RoutineVO(String name, String description, float levelCategory1, float levelCategory2, int duration, boolean favorited) {
        this.name = name;
        this.description = description;
        this.levelCategory1 = levelCategory1;
        this.levelCategory2 = levelCategory2;
        this.duration = duration;
        this.favorited = favorited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLevelCategory1() {
        return levelCategory1;
    }

    public void setLevelCategory1(float levelCategory1) {
        this.levelCategory1 = levelCategory1;
    }

    public float getLevelCategory2() {
        return levelCategory2;
    }

    public void setLevelCategory2(float levelCategory2) {
        this.levelCategory2 = levelCategory2;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
