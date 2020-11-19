package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiCycleService;
import com.example.getkracking.API.ApiExerciseService;
import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiRoutineService;
import com.example.getkracking.API.model.CycleModel;
import com.example.getkracking.API.model.ExerciseModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.vo.AbsentLiveData;
import com.example.getkracking.vo.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class RoutineRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoutineService routineService;
    private ApiCycleService cycleRepository;
    private ApiExerciseService exerciseService;
    private AppDatabase database;
    private List<String> difficultyList;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    String[] difficultyVector = {"rookie", "beginner", "intermediate", "advanced", "expert"};

    public RoutineRepository(AppExecutors executors, ApiRoutineService routineService, ApiCycleService cycleService, ApiExerciseService exerciseService, AppDatabase database) {
        difficultyList = new ArrayList<String>();
        for (String aux : difficultyVector) {
            difficultyList.add(aux);
        }
        this.executors = executors;
        this.routineService = routineService;
        this.cycleRepository = cycleService;
        this.exerciseService = exerciseService;
        this.database = database;
    }

    public LiveData<Resource<List<RoutineVO>>> getRoutines() {

        return new NetworkBoundResource<List<RoutineVO>, List<RoutineTable>, PagedListModel<RoutineModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(routineEntity -> new RoutineVO(routineEntity.name, routineEntity.detail, routineEntity.creator,
                                    "placeholder", routineEntity.difficulty, 3,
                                    69, routineEntity.id, routineEntity.favourite != 0, routineEntity.rating))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineTable(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getCreatorModel().getUsername(), 0, routineModel.getAverageRating(), castDifficulty(routineModel.getDifficulty()))
                            ).collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineVO(routineModel.getName(), routineModel.getDetail(), routineModel.getCreatorModel().getUsername(),
                                    "placeholder", castDifficulty(routineModel.getDifficulty()), 3,
                                    69, routineModel.getId(), false /*TODO: no hardcodear*/, routineModel.getAverageRating()))
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
                return routineService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<RoutineVO>>> getFavouriteRoutines() {

        return new NetworkBoundResource<List<RoutineVO>, List<RoutineTable>, PagedListModel<RoutineModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(routineEntity -> new RoutineVO(routineEntity.name, routineEntity.detail, routineEntity.creator,
                                    "placeholder", routineEntity.difficulty, 3,
                                    69, routineEntity.id, routineEntity.favourite != 0, routineEntity.rating))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineTable(routineModel.getId(), routineModel.getName(),
                                    routineModel.getDetail(), routineModel.getCreatorModel().getUsername(),
                                    0, routineModel.getAverageRating(), castDifficulty(routineModel.getDifficulty()))
                            ).collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineVO(routineModel.getName(), routineModel.getDetail(), routineModel.getCreatorModel().getUsername(),
                                    "placeholder", castDifficulty(routineModel.getDifficulty()), 3,
                                    69, routineModel.getId(), false, routineModel.getAverageRating()))
                            .collect(toList());
                }) {
            @Override //no me preocupo por esto, el getAllRoutines lo hace por mi
            protected void saveCallResult(@NonNull List<RoutineTable> entities) {
                //saco las rutinas favoritas y las vuelvo a agregar, quizás me las favvearon
                for (RoutineTable entity : entities) {
                    database.routineDao().deleteRoutine(entity);
                    database.routineDao().addRoutine(entity);
                }
            }

            @Override //siempre hago fetch por si me cambiaron algo
            protected boolean shouldFetch(@Nullable List<RoutineTable> entities) {
                return true;
            }

            @Override //teóricamente, ya están cargadas en la base de datos local
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override //busco de mi base de datos para recuperar las que tengo marcadas
            protected LiveData<List<RoutineTable>> loadFromDb() {
                return database.routineDao().getFavouriteRoutines();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return routineService.getFavouriteRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineVO>> addToFavourites(int routineId) {
        return new NetworkBoundResource<RoutineVO, RoutineTable, Void>(executors,
                table -> {
                    return new RoutineVO(table.name, table.detail, table.creator,
                            "placeholder", table.difficulty, 3,
                            69, table.id, true, table.rating);
                },
                null,
                null
                /*model -> {
                    return new RoutineTable(model.getId(), model.getName(),
                            model.getDetail(), model.getCreatorModel().getUsername(),
                            1, model.getAverageRating(), castDifficulty(model.getDifficulty()));
                },
                model -> {
                    return new RoutineVO(model.getName(), model.getDetail(), model.getCreatorModel().getUsername(),
                            "placeholder", castDifficulty(model.getDifficulty()), 3,
                            69, model.getId(), true, model.getAverageRating());
                }*/
        ) {
            @Override
            protected void saveCallResult(@NonNull RoutineTable entity) {
                database.routineDao().deleteRoutine(entity);
                database.routineDao().addRoutine(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineTable entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineTable> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {

                return routineService.addToFavourites(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineVO>> removeFromFavourites(int routineId) {
        return new NetworkBoundResource<RoutineVO, RoutineTable, Void>(executors,
                table -> {
                    return new RoutineVO(table.name, table.detail, table.creator,
                            "placeholder", table.difficulty, 3,
                            69, table.id, false, table.rating);
                },
                null,
                null) {
            @Override
            protected void saveCallResult(@NonNull RoutineTable entity) {
                database.routineDao().deleteRoutine(entity);
                database.routineDao().addRoutine(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineTable entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineTable> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return routineService.removeFromFavourites(routineId);
            }
        }.asLiveData();
    }

    private int castDifficulty(String difficulty) {
        return difficultyList.indexOf(difficulty);
    }

    //----------------------------------CYCLE REPOSITORY------------------------------------

    public LiveData<Resource<List<CycleVO>>> getCycles(int routineId) {

        return new NetworkBoundResource<List<CycleVO>, List<CycleTable>, PagedListModel<CycleModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(cycleEntity -> new CycleVO(cycleEntity.cycleId, cycleEntity.name, new ArrayList<ExerciseVO>(), cycleEntity.order))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycleModel -> new CycleTable(cycleModel.getId(), routineId, cycleModel.getDetail(), cycleModel.getName(), cycleModel.getOrder()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycleModel -> new CycleVO(cycleModel.getId(), cycleModel.getName(), new ArrayList<ExerciseVO>(), cycleModel.getOrder()))
                            .collect(toList());
                }) {
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
                return database.cycleDao().getAllCycles(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<CycleModel>>> createCall() {
                return cycleRepository.getCycles(routineId);
            }
        }.asLiveData();
    }

    //---------------------------------------EXERCISE REPOSITORY----------------------------------------
    public LiveData<Resource<List<ExerciseVO>>> getExercises(int routineId, int cycleId) {

        return new NetworkBoundResource<List<ExerciseVO>, List<ExerciseTable>, PagedListModel<ExerciseModel>>(executors,
                entities -> {
                    return entities.stream()
                            .map(exerciseEntity -> new ExerciseVO(exerciseEntity.exerciseName, exerciseEntity.exerciseDetail, exerciseEntity.repetitions,
                                    exerciseEntity.duration, exerciseEntity.media))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exerciseModel -> new ExerciseTable(exerciseModel.getId(), exerciseModel.getDetail(), exerciseModel.getName(),
                                    cycleId, exerciseModel.getDuration(), exerciseModel.getOrder(), exerciseModel.getRepetitions(), "xd "))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exerciseModel -> new ExerciseVO(exerciseModel.getName(), exerciseModel.getDetail(), exerciseModel.getRepetitions(),
                                    exerciseModel.getDuration(), "xd"))
                            .collect(toList());
                }) {
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
                return database.exerciseDao().getAllfromCycle(cycleId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<ExerciseModel>>> createCall() {
                return exerciseService.getExercises(routineId, cycleId);
            }
        }.asLiveData();
    }


}
