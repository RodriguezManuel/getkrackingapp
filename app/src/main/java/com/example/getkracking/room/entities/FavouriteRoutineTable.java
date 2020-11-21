package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.getkracking.entities.RoutineVO;

@Entity(tableName = "favouriteRoutineTable")
public class FavouriteRoutineTable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Detail")
    public String detail;

    @ColumnInfo(name = "Creator")
    public String creator;

    @ColumnInfo(name = "Favourite")
    public int favourite;

    @ColumnInfo(name = "Rating")
    public float rating;

    @ColumnInfo(name = "Difficulty")
    public int difficulty;

    @ColumnInfo(name = "DateCreated")
    public long datecreated;

    public FavouriteRoutineTable(int id, String name, String detail, String creator, int favourite, float rating, int difficulty, long datecreated) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.creator = creator;
        this.favourite = favourite;
        this.rating = rating;
        this.difficulty = difficulty;
        this.datecreated = datecreated;
    }

    public RoutineVO toVo(){
        return new RoutineVO(name, detail, creator, "placeholder", difficulty, 3, 69, id, true, rating, datecreated);
    }
}
