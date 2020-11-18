package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiCycleService;
import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiRoutineService;
import com.example.getkracking.API.model.CycleModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.vo.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class CycleRepository {


    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiCycleService service;
    private AppDatabase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public CycleRepository(AppExecutors executors, ApiCycleService service, AppDatabase database) {

        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    /*
    public CycleVO(int id, String name, ArrayList<ExerciseVO> exercises , int order) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.order = order;
    }
     public CycleTable( int cycleId , int routineId , String detail , int order){
        this.cycleId = cycleId;
        this.routineId = routineId;
        this.detail = detail;
        this.order = order;
    }

     */
    public LiveData<Resource<List<CycleVO>>> getCycles(int routineId) {

        return new NetworkBoundResource<List<CycleVO>, List<CycleTable>, PagedListModel<CycleModel>>(executors,
                                                               entities -> {
                    return entities.stream()
                            .map(cycleEntity -> new CycleVO(cycleEntity.cycleId , cycleEntity.name , new ArrayList<ExerciseVO>() , cycleEntity.order))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycleModel -> new CycleTable(cycleModel.getId() , routineId , cycleModel.getDetail() , cycleModel.getName() , cycleModel.getOrder()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycleModel -> new CycleVO(cycleModel.getId() , cycleModel.getName() , new ArrayList<ExerciseVO>() , cycleModel.getOrder()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<CycleTable> entities) {
                database.cycleDao().deleteAll();
                database.cycleDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CycleTable> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<CycleModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CycleTable>> loadFromDb() {
                return database.cycleDao().getAllCycles( routineId );
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<CycleModel>>> createCall() {
                return service.getCycles(routineId);
            }
        }.asLiveData();
    }

}

