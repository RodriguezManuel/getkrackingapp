package com.example.getkracking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.R;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.vo.Resource;

import java.util.List;

public class RoutineInfoViewModel extends RepositoryViewModel<RoutineRepository> {
    MutableLiveData<Boolean> chipText = new MutableLiveData<>(false);

    public RoutineInfoViewModel(RoutineRepository repository) {
        super(repository);
    }

    public MutableLiveData<Boolean> getChipText() {
        return chipText;
    }

    public void changeChipText() {
        chipText.setValue(!chipText.getValue());
    }

    public LiveData<Resource<List<ExerciseVO>>> getExercises(int routineId, int cycleId) {
        return repository.getExercises(routineId, cycleId);
    }

    public LiveData<Resource<List<CycleVO>>> getCycles(int routineId) {
        return repository.getCycles(routineId);
    }

    public LiveData<Resource<Void>> addToFavourites(int routineId) {
        return repository.addToFavourites(routineId);
    }

    public LiveData<Resource<Void>> removeFromFavourites(int routineId) {
        return repository.removeFromFavourites(routineId);
    }

    public LiveData<Resource<List<RoutineVO>>> getFavouriteRoutines() {
        return repository.getFavouriteRoutines();
    }
}
