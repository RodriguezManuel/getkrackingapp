package com.example.getkracking.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.getkracking.room.entities.UserTable;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from userTable")
    LiveData<List<UserTable>> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTable user);

    @Query("DELETE FROM userTable")
    void deleteAll();
}
