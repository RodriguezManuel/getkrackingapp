package com.example.getkracking.API.model;

import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.room.entities.CategoryTable;

import com.example.getkracking.entities.CategoryVO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;

    /**
     * No args constructor for use in serialization
     */
    public CategoryModel() {
    }

    /**
     * @param name
     * @param id
     * @param detail
     */
    public CategoryModel(int id, String name, String detail) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public CategoryTable toTable(){
        return new CategoryTable(this.id , this.name , this.detail);
    }

    public CategoryVO toVo(){
        return new CategoryVO(this.id , this.name , this.detail);
    }
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
}
