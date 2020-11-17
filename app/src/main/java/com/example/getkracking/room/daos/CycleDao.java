package com.example.getkracking.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.getkracking.room.entities.CycleTable;

import java.util.List;
@Dao
public interface CycleDao {
    @Query("select * from cycleTable where RoutineId = :myroutineid order by `order` desc")
    List<CycleTable> getAllCycles(int myroutineid);
    @Insert
    void addCycle(CycleTable cycle);
    @Delete
    void deleteCycle(CycleTable cycle);
}
