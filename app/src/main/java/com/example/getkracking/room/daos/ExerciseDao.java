package com.example.getkracking.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.getkracking.room.entities.ExerciseTable;

import java.util.List;
@Dao
public interface ExerciseDao {
        @Query("select * from exercisetable where cycleId = :mycycleid")
        LiveData<List<ExerciseTable>> getAllfromCycle(int mycycleid );

        @Query("select * from exercisetable where cycleId in (:cycleidlist)")
        LiveData<List<ExerciseTable>> getAllfromCycle( List<Integer> cycleidlist);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(List<ExerciseTable> exercises);

        @Insert
        void addExercise( ExerciseTable ...  exercise);

        @Query("DELETE FROM exerciseTable")
        void deleteAll();



}
