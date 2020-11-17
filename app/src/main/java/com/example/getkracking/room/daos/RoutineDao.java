package com.example.getkracking.room.daos;

import android.content.pm.LabeledIntent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.getkracking.room.entities.RoutineTable;

import java.util.List;

@Dao
public interface  RoutineDao {
    @Query("select * from routineTable" )
    LiveData<List<RoutineTable>> getAllRutines();

    @Query("select * from routineTable where id = :myid")
    LiveData<RoutineTable> getRoutineById(int myid);

    @Query("select * from routineTable where favourite = 1")
    LiveData<List<RoutineTable>> getFavRoutines();

    @Insert
    void addRoutine(RoutineTable ... routine);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insert(List<RoutineTable> routine);

    @Delete
    void deleteRoutine(RoutineTable ... routine);

    @Query("DELETE FROM routineTable")
    void deleteAll();
}

