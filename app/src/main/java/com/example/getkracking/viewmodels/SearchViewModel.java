package com.example.getkracking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.vo.Resource;

import java.util.List;

public class SearchViewModel extends RepositoryViewModel<RoutineRepository> {

    public SearchViewModel(RoutineRepository repository) {
        super(repository);
    }

    public LiveData<Resource<List<RoutineVO>>> getRoutines() {
        return repository.getRoutines();
    }

    public LiveData<Resource<List<RoutineVO>>> getFavouriteRoutines() {
        return repository.getFavouriteRoutines();
    }
}
