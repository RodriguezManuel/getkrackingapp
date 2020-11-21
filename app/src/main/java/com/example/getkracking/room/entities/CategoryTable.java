package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.getkracking.entities.CategoryVO;

@Entity(tableName = "categoryTable")
public class CategoryTable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Detail")
    public String detail;

    public CategoryTable(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public CategoryVO toVO(){
        return new CategoryVO(id, name, detail);
    }

    public int getId() {
        return id;
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
