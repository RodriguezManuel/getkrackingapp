package com.example.getkracking.room.entities;

import androidx.room.DatabaseView;

@DatabaseView("SELECT routineTable.id , cycleTable.id , cycleTable.detail , cycleTable , exerciseId ")
public class FullRoutineView {
}
