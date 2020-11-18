package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiCycleService;
import com.example.getkracking.API.ApiExerciseService;
import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.model.CycleModel;
import com.example.getkracking.API.model.ExerciseModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.vo.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class ExerciseRepository {


    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiExerciseService service;
    private AppDatabase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public ExerciseRepository(AppExecutors executors, ApiExerciseService service, AppDatabase database) {

        this.executors = executors;
        this.service = service;
        this.database = database;
    }
    /*
    public ExerciseVO(String name, String desc, int quantity, int duration, String imagesrc) {
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.duration = duration;
        this.imagesrc = imagesrc;
    }

    public ExerciseTable( int exerciseId , String exerciseName , String exerciseDetail , int cycleId , int duration , int order , int repetitions , String media){
        this.exerciseId = exerciseId;
        this.exerciseDetail = exerciseDetail;
        this.exerciseName = exerciseName;
        this.cycleId = cycleId;
        this.duration = duration;
        this.order = order;
        this.repetitions = repetitions;
        this.media = media;
    }
     */
    public LiveData<Resource<List<ExerciseVO>>> getExercises(int routineId , int cycleId) {

        return new NetworkBoundResource<List<ExerciseVO>, List<ExerciseTable>, PagedListModel<ExerciseModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(  exerciseEntity -> new ExerciseVO(exerciseEntity.exerciseName , exerciseEntity.exerciseDetail , exerciseEntity.repetitions ,
                                    exerciseEntity.duration , exerciseEntity.media))
                                    .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exerciseModel -> new ExerciseTable(exerciseModel.getId() , exerciseModel.getDetail(), exerciseModel.getName(),
                                    cycleId , exerciseModel.getDuration() , exerciseModel.getOrder() , exerciseModel.getRepetitions(), "xd " ))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exerciseModel -> new ExerciseVO(exerciseModel.getName() , exerciseModel.getDetail() , exerciseModel.getRepetitions(),
                                    exerciseModel.getDuration() , "xd") )
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<ExerciseTable> entities) {
                database.exerciseDao().deleteAll();
                database.exerciseDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ExerciseTable> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<ExerciseModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ExerciseTable>> loadFromDb() {
                return database.exerciseDao().getAllfromCycle( cycleId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<ExerciseModel>>> createCall() {
                return service.getExercises(routineId , cycleId);
            }
        }.asLiveData();
    }

}
