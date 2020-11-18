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
        excercisesList.add(new ExerciseVO("Sentadillas","asdawldql[qs[d;qsdq", 40, 0,"A"));
        excercisesList.add(new ExerciseVO("Abominables", "dqpdwd[lsadl qla psd  asl [p",0, 50,"A"));
        excercisesList.add(new ExerciseVO("Los de culo", "sadoqwdk pq ksdpq o qk p ",0, 20,"A"));
    }
}