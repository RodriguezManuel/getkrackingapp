package com.example.getkracking.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.getkracking.room.entities.ExerciseTable;

import java.util.List;
@Dao
public interface ExerciseDao {
        @Query("select * from exercisetable where cycleId = :mycycleid")
        List<ExerciseTable> getAllfromCycle( int mycycleid );
        @Query("select * from exercisetable where cycleId in (:cycleidlist)")
        List<ExerciseTable> getAllfromCycle( List<Integer> cycleidlist);
        @Insert
        void addExercise( ExerciseTable ...  exercise);




}
