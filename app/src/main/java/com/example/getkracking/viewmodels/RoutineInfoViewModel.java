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
        excercisesList.add(new ExerciseVO("Sentadillas","asdawldql[qs[d;qsdq", 40, 0));
        excercisesList.add(new ExerciseVO("Abominables", "dqpdwd[lsadl qla psd  asl [p",0, 50));
        excercisesList.add(new ExerciseVO("Los de culo", "sadoqwdk pq ksdpq o qk p ",0, 20));
        excercisesList.add(new ExerciseVO("Los de culo", "doqd p kdpo aksdoakd poawkd ",0, 20));
        excercisesList.add(new ExerciseVO("Los de culo", "d oaskd poakw ok  kp askp ",21, 0));
        excercisesList.add(new ExerciseVO("Los de culo", "d p3 l1ok3 12ke oska; d", 17, 0));
    }
}