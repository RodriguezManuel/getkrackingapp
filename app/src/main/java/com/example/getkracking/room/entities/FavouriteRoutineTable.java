package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    public int rating;

    @ColumnInfo(name = "Difficulty")
    public int difficulty;

    public FavouriteRoutineTable(int id, String name, String detail, String creator, int favourite, int rating, int difficulty) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.creator = creator;
        this.favourite = favourite;
        this.rating = rating;
        this.difficulty = difficulty;
    }
}
