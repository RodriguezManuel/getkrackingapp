package com.example.getkracking.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.getkracking.entities.ExerciseVO;

import java.util.ArrayList;

public class RoutineInfoViewModel extends ViewModel {
    ArrayList<ExerciseVO> excercisesList = new ArrayList<>();

    public ArrayList<ExerciseVO> getExcercisesList() {
        fillList();
        return excercisesList;
    }

    private void fillList(){
        //HARDCODEADO CONECTAR A API
        excercisesList.add(new ExerciseVO("Sentadillas", "40 repeticiones"));
        excercisesList.add(new ExerciseVO("Abominables", "50 repeticiones"));
        excercisesList.add(new ExerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExerciseVO("Los de culo", "20 minutos"));
    }
}