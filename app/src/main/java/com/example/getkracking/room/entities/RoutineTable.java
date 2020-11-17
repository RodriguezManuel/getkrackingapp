package com.example.getkracking.room.entities;

import androidx.recyclerview.widget.SortedList;
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

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Detail")
    public String detail;

    @ColumnInfo(name = "Creator")
    public int creator;

    @ColumnInfo(name = "Favourite")
    public int favourite;

    @ColumnInfo(name = "Rating")
    public int rating;
}
