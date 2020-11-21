package com.example.getkracking.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//import com.example.getkracking.room.daos.RoutineDao;
//import com.example.getkracking.room.entities.CategoryTable;
import com.example.getkracking.room.daos.CategoryDao;
import com.example.getkracking.room.daos.CycleDao;
import com.example.getkracking.room.daos.ExerciseDao;
import com.example.getkracking.room.daos.FavouriteRoutineDao;
import com.example.getkracking.room.daos.RoutineDao;
import com.example.getkracking.room.daos.UserDao;
import com.example.getkracking.room.entities.CategoryTable;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.room.entities.FavouriteRoutineTable;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.room.entities.UserTable;

@Database( entities = {RoutineTable.class , FavouriteRoutineTable.class , ExerciseTable.class , CycleTable.class, UserTable.class , CategoryTable.class} , version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();

    public abstract FavouriteRoutineDao favouriteRoutineDao();

    public abstract UserDao userDao();

    public abstract ExerciseDao exerciseDao();

    public abstract CycleDao cycleDao();

    public abstract CategoryDao categoryDao();
}
