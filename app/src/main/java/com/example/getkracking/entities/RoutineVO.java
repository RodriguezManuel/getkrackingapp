package com.example.getkracking.entities;

//DEBERIA TRANSFORMARSE EN DAO

import com.example.getkracking.room.entities.FavouriteRoutineTable;

import java.util.Date;

public class RoutineVO {
    private String name, description, creator, category;
    private int levelCategory1, levelCategory2, duration, id;
    private float rating;
    private boolean favorited;
    private Long dateCreated;
    public RoutineVO(String name, String description, String creator, String category, int difficulty, int levelCategory2,
                     int duration, int id, boolean favorited, float rating, Long dateCreated) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.category = category;
        this.levelCategory1 = difficulty;
        this.levelCategory2 = levelCategory2;
        this.duration = duration;
        this.id = id;
        this.favorited = favorited;
        this.rating = rating;
        this.dateCreated = dateCreated;
    }

    public FavouriteRoutineTable toFavTable(){
        return new FavouriteRoutineTable(id, name, description, creator, 1, rating, levelCategory1, dateCreated);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
