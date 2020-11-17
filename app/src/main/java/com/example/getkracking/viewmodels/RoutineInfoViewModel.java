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
        excercisesList.add(new ExerciseVO("Sentadillas", 40, 0));
        excercisesList.add(new ExerciseVO("Abominables", 0, 50));
        excercisesList.add(new ExerciseVO("Los de culo", 0, 20));
        excercisesList.add(new ExerciseVO("Los de culo", 0, 20));
        excercisesList.add(new ExerciseVO("Los de culo", 21, 0));
        excercisesList.add(new ExerciseVO("Los de culo", 17, 0));
    }
}