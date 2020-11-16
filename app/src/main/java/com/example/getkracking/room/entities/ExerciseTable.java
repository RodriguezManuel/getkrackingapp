package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity
public class ExerciseTable {

    @PrimaryKey
    public int exerciseId;

    @ColumnInfo(name = "ExerciseName")
    public String exerciseName;

    @ColumnInfo(name = "ExerciseDetail")
    public String exerciseDetail;

    @ForeignKey(entity = CycleTable.class , childColumns = "CycleId" , parentColumns = "CycleId")
    public int cycleId;

    @ColumnInfo(name = "Media")
    public String[] media;

}
