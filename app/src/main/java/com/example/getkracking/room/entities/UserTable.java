package com.example.getkracking.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName =  "userTable")
public class UserTable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Username")
    public String username;

    @ColumnInfo(name = "Image")
    public String image;

    @ColumnInfo(name = "Name")
    public String fullName;

    @ColumnInfo(name = "Email")
    public String email;


    public UserTable(int id, String username, String image, String fullName, String email) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.fullName = fullName;
        this.email = email;
    }
}


