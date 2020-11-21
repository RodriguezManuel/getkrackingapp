package com.example.getkracking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.API.model.ExecutionAnswerModel;
import com.example.getkracking.API.model.ReviewAnswerModel;
import com.example.getkracking.entities.CategoryVO;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.vo.Resource;

import java.util.List;

public class RoutinesViewModel extends RepositoryViewModel<RoutineRepository> {

    public RoutinesViewModel(RoutineRepository repository) {
        super(repository);
    }

    public LiveData<Resource<List<RoutineVO>>> getRoutines() {
        return repository.getRoutines();
    }

    public LiveData<Resource<List<RoutineVO>>> getFavouriteRoutines() {
        return repository.getFavouriteRoutines();
    }
    public LiveData<Resource<List<CategoryVO>>> getCategories(){ return repository.getCategories(); }

    public LiveData<Resource<ReviewAnswerModel>> postReview( int routineId , int score){ return repository.postReview(routineId,score);}

    public LiveData<Resource<ExecutionAnswerModel>> executeRoutine(int routineId){return repository.executeRoutine(routineId); }

}
