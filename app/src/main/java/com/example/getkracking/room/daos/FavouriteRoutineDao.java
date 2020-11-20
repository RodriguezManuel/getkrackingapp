package com.example.getkracking.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.getkracking.room.entities.FavouriteRoutineTable;

import java.util.List;

@Dao
public interface FavouriteRoutineDao {
    @Query("select * from favouriteRoutineTable where favourite = 1")
    LiveData<List<FavouriteRoutineTable>> getFavouriteRoutines();

    @Insert
    void addFavouriteRoutine(FavouriteRoutineTable... routine);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(List<FavouriteRoutineTable> routine);

    @Delete
    void deleteFavouriteRoutine(FavouriteRoutineTable ... routine);

    @Query("DELETE FROM favouriteRoutineTable")
    void deleteAllFavourites();
}
