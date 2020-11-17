package com.example.getkracking.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.getkracking.room.entities.RoutineTable;

import java.util.List;

@Dao
public interface  RoutineDao {
    @Query("select * from routineTable" )
    List<RoutineTable> getAllRutines();

    @Query("select * from routineTable where id = :myid")
    RoutineTable getRoutineById(int myid);

    @Query("select * from routineTable where favourite = 1")
    List<RoutineTable> getFavRoutines();

    @Insert
    void addRoutine(RoutineTable ... routine);

    @Delete
    void deleteRoutine(RoutineTable ... routine);
}

