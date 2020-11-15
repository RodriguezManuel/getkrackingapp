package com.example.getkracking.entities;

//DEBERIA TRANSFORMARSE EN DAO

public class RoutineVO {
    private String name, description, creator, category;
    private int levelCategory1, levelCategory2, duration, id;   // en minutos
    private boolean favorited;

    public RoutineVO(String name, String description, String creator, String category, int levelCategory1, int levelCategory2, int duration, int id, boolean favorited) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.category = category;
        this.levelCategory1 = levelCategory1;
        this.levelCategory2 = levelCategory2;
        this.duration = duration;
        this.id = id;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLevelCategory1() {
        return levelCategory1;
    }

    public void setLevelCategory1(int levelCategory1) {
        this.levelCategory1 = levelCategory1;
    }

    public int getLevelCategory2() {
        return levelCategory2;
    }

    public void setLevelCategory2(int levelCategory2) {
        this.levelCategory2 = levelCategory2;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
