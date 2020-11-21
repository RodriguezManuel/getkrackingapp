package com.example.getkracking.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.RoutineTable;

import java.util.List;
@Dao
public interface CycleDao {
    @Query("select * from cycleTable where RoutineId = :myroutineid order by `order` desc")
    LiveData<List<CycleTable>> getAllCycles(int myroutineid);
    @Insert
    void addCycle(CycleTable cycle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CycleTable> cycles);

    @Delete
    void deleteCycle(CycleTable cycle);

    @Query("DELETE FROM cycleTable")
    void deleteAll();
}
