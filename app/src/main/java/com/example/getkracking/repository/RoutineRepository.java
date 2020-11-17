package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiRoutineService;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.API.model.TokenModel;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.vo.AbsentLiveData;
import com.example.getkracking.vo.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class RoutineRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoutineService service;
    private AppDatabase database;
    private List<String> difficultyList;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    String[] difficultyVector = {"rookie", "beginner", "intermediate", "advanced", "expert"};

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, AppDatabase database) {
        difficultyList = new ArrayList<String>();
        for (String aux : difficultyVector) {
            difficultyList.add(aux);
        }
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    /*
       public RoutineVO(String name, String description, String creator, String category, int levelCategory1, int levelCategory2, int duration, int id, boolean favorited) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.category = category;
        this.levelCategory1 = levelCategory1;
        this.levelCategory2 = levelCategory2;
        this.duration = duration;
        this.id = id;
        this.favorited = favorited;
    }
     */
    public LiveData<Resource<List<RoutineVO>>> getRoutines() {

        return new NetworkBoundResource<List<RoutineVO>, List<RoutineTable>, PagedListModel<RoutineModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(routineEntity -> new RoutineVO(routineEntity.name, routineEntity.detail, routineEntity.detail,
                                    routineEntity.creator, routineEntity.difficulty, 3,
                                    69, routineEntity.id, routineEntity.favourite != 0))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineTable(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getCreatorModel().getUsername(), 0, routineModel.getAverageRating(), castDifficulty(routineModel.getDifficulty()))
                            ).collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineVO(routineModel.getName(), routineModel.getDetail(), routineModel.getDetail(),
                                    routineModel.getCreatorModel().getUsername(), castDifficulty(routineModel.getDifficulty()), 3,
                                    69, routineModel.getId(), false))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<RoutineTable> entities) {
                database.routineDao().deleteAll();
                database.routineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineTable> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineTable>> loadFromDb() {
                return database.routineDao().getAllRutines();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return service.getRoutines();
            }
        }.asLiveData();
    }

    private int castDifficulty(String difficulty) {
        return difficultyList.indexOf(difficulty);
    }
}
