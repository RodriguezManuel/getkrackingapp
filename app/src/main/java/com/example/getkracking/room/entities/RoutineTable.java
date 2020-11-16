package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "routineTable")
public class RoutineTable {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "detail")
    public String detail;

    @ColumnInfo(name = "creator")
    public int creator;

    @ColumnInfo(name = "favourite")
    public Boolean favourite;

    @ColumnInfo(name = "rating")
    public int rating;

}
