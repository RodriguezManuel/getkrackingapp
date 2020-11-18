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

    @ColumnInfo(name ="ExerciseDuration")
    public int duration;

    @ColumnInfo(name = "ExerciseOrder")
    public int order;

    @ColumnInfo(name = "Repetitions")
    public int repetitions;

    @ColumnInfo(name = "Media")
    public String media;

    @ForeignKey(entity = CycleTable.class , childColumns = "CycleId" , parentColumns = "CycleId")
    public int cycleId;


    public ExerciseTable( int exerciseId , String exerciseName , String exerciseDetail , int cycleId , int duration , int order , int repetitions , String media){
        this.exerciseId = exerciseId;
        this.exerciseDetail = exerciseDetail;
        this.exerciseName = exerciseName;
        this.cycleId = cycleId;
        this.duration = duration;
        this.order = order;
        this.repetitions = repetitions;
        this.media = media;
    }
}
