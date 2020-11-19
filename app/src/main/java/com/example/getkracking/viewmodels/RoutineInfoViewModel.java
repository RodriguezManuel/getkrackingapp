package com.example.getkracking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.R;

public class RoutineInfoViewModel extends AndroidViewModel {
    MutableLiveData<String> chipText = new MutableLiveData<>();

    public RoutineInfoViewModel(@NonNull Application application) {
        super(application);
        chipText.setValue(getApplication().getResources().getString(R.string.exercise_execution_exercise_mode));
    }

    public MutableLiveData<String> getChipText() {
        return chipText;
    }

    public void changeChipText() {
        if(chipText.getValue().equals(getApplication().getResources().getString(R.string.exercise_execution_exercise_mode)))
            chipText.setValue(getApplication().getResources().getString(R.string.exercise_execution_list_mode));
        else chipText.setValue(getApplication().getResources().getString(R.string.exercise_execution_exercise_mode));
    }
}
