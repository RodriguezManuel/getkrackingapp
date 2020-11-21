package com.example.getkracking.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.getkracking.room.entities.CategoryTable;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    public void insert(CategoryTable category);

    @Insert
    public void insert(List<CategoryTable> category);

    @Query("select * from categoryTable")
    public LiveData<List<CategoryTable>> getAll();

    @Query("delete from categoryTable")
    public void deleteAll();
}
