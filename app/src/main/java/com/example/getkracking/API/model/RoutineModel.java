package com.example.getkracking.API.model;

import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RateLimiter;
import com.example.getkracking.room.entities.FavouriteRoutineTable;
import com.example.getkracking.room.entities.RoutineTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoutineModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("dateCreated")
    @Expose
    private Long dateCreated;
    @SerializedName("averageRating")
    @Expose
    private int averageRating;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("creator")
    @Expose
    private CreatorModel creatorModel;
    @SerializedName("category")
    @Expose
    private CategoryModel categoryModel;

    /**
     * No args constructor for use in serialization
     *
     */
    public RoutineModel() {
    }

    /**
     *
     * @param difficulty
     * @param creatorModel
     * @param dateCreated
     * @param averageRating
     * @param name
     * @param isPublic
     * @param id
     * @param detail
     * @param categoryModel
     */
    public RoutineModel(int id, String name, String detail, Long dateCreated, int averageRating, boolean isPublic, String difficulty, CreatorModel creatorModel, CategoryModel categoryModel) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creatorModel = creatorModel;
        this.categoryModel = categoryModel;
    }
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.SECONDS);
    String[] difficultyVector = {"rookie", "beginner", "intermediate", "advanced", "expert"};

    public RoutineVO toVO(){
        return new RoutineVO(name, detail, creatorModel.getUsername(), categoryModel.getName(), castDifficulty(difficulty), 3, 69, id, false, averageRating, dateCreated);
    }

    public RoutineTable toTable(int favourited){
        return new RoutineTable(id, name, detail, creatorModel.getUsername(),  favourited, averageRating, castDifficulty(difficulty), dateCreated);
    }

    public FavouriteRoutineTable toFavTable(){
        return new FavouriteRoutineTable(id, name, detail, creatorModel.getUsername(),  1, averageRating, castDifficulty(difficulty), dateCreated);
    }


    private int castDifficulty(String difficulty) {
        return difficultyList.indexOf(difficulty);
    }

    private List<String> difficultyList = Arrays.asList(difficultyVector);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public CreatorModel getCreatorModel() {
        return creatorModel;
    }

    public void setCreatorModel(CreatorModel creatorModel) {
        this.creatorModel = creatorModel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

}