package com.example.getkracking.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.getkracking.API.model.Category;
//import com.example.getkracking.room.daos.RoutineDao;
//import com.example.getkracking.room.entities.CategoryTable;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.room.entities.RoutineTable;

@Database(entities = {RoutineTable.class , ExerciseTable.class , CycleTable.class } , version = 1)
public abstract class AppDatabase extends RoomDatabase {
//    public abstract RoutineDao routineDao();
}
