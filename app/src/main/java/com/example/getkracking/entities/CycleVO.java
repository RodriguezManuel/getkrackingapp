package com.example.getkracking.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CycleVO implements Parcelable {

    private String name;
    private ArrayList<ExerciseVO> exercises;
    private int order;
    private int id;

    public CycleVO(int id, String name, ArrayList<ExerciseVO> exercises , int order) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExercises(ArrayList<ExerciseVO> exercises) {
        this.exercises = exercises;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ExerciseVO> getExercises() {
        return exercises;
    }

    //METODOS PARA EL NAVIGATION ENTRE INFORUTINA Y EJECUCION
    protected CycleVO(Parcel in) {
        name = in.readString();
        order = in.readInt();
        id = in.readInt();
    }

    public static final Creator<CycleVO> CREATOR = new Creator<CycleVO>() {
        @Override
        public CycleVO createFromParcel(Parcel in) {
            return new CycleVO(in);
        }

        @Override
        public CycleVO[] newArray(int size) {
            return new CycleVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(id);
    }
}
