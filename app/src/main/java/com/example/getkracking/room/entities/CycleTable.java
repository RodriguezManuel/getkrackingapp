package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "cycleTable")
public class CycleTable {

    @PrimaryKey
    public int cycleId;

    @ForeignKey(entity = RoutineTable.class , parentColumns = "id" , childColumns = "routineId")
    public int routineId;

    @ColumnInfo(name = "Detail")
    public String detail;

    @ColumnInfo(name = "Order")
    public int order;


}
