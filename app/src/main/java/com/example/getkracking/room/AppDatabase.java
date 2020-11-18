package com.example.getkracking.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//import com.example.getkracking.room.daos.RoutineDao;
//import com.example.getkracking.room.entities.CategoryTable;
import com.example.getkracking.room.daos.CycleDao;
import com.example.getkracking.room.daos.ExerciseDao;
import com.example.getkracking.room.daos.RoutineDao;
import com.example.getkracking.room.daos.UserDao;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.room.entities.UserTable;

@Database( entities = {RoutineTable.class , ExerciseTable.class , CycleTable.class, UserTable.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();

    public abstract UserDao userDao();

    public abstract ExerciseDao exerciseDao();

    public abstract CycleDao cycleDao();
}
