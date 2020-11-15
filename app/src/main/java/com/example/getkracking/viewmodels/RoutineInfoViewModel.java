package com.example.getkracking.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.getkracking.entities.ExcerciseVO;

import java.util.ArrayList;

public class RoutineInfoViewModel extends ViewModel {
    ArrayList<ExcerciseVO> excercisesList = new ArrayList<>();

    public ArrayList<ExcerciseVO> getExcercisesList() {
        fillList();
        return excercisesList;
    }

    private void fillList(){
        //HARDCODEADO CONECTAR A API
        excercisesList.add(new ExcerciseVO("Sentadillas", "40 repeticiones"));
        excercisesList.add(new ExcerciseVO("Abominables", "50 repeticiones"));
        excercisesList.add(new ExcerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExcerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExcerciseVO("Los de culo", "20 minutos"));
        excercisesList.add(new ExcerciseVO("Los de culo", "20 minutos"));
    }
}