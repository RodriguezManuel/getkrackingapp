package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiCycleService;
import com.example.getkracking.API.ApiExerciseService;
import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiRoutineService;
import com.example.getkracking.API.model.CategoryModel;
import com.example.getkracking.API.model.CycleModel;
import com.example.getkracking.API.model.ExecutionAnswerModel;
import com.example.getkracking.API.model.ExecutionModel;
import com.example.getkracking.API.model.ExerciseModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.ReviewAnswerModel;
import com.example.getkracking.API.model.ReviewModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.entities.CategoryVO;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.CategoryTable;
import com.example.getkracking.room.entities.CycleTable;
import com.example.getkracking.room.entities.ExerciseTable;
import com.example.getkracking.room.entities.FavouriteRoutineTable;
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
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.SECONDS);
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

    public LiveData<Resource<RoutineVO>> getRoutineById(int id) {
        return new NetworkBoundResource<RoutineVO, RoutineTable, RoutineModel>(executors,
                table -> {
                    return table.toVo(false);
                },
                model -> {
                    return model.toTable(0);
                },
                model -> {
                    return model.toVO();
                }
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
            protected boolean shouldPersist(@Nullable RoutineModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineTable> loadFromDb() {
                return database.routineDao().getRoutineById(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineModel>> createCall() {
                return routineService.getRoutineById(id);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<RoutineVO>>> getRoutines() {

        return new NetworkBoundResource<List<RoutineVO>, List<RoutineTable>, PagedListModel<RoutineModel>>(executors,
                tables -> {
                    return tables.stream()
                            .map(table -> table.toVo(false))
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model -> model.toTable(0))
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model ->  model.toVO())
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
    public LiveData<Resource<List<RoutineVO>>> searchRoutines(String string) {

        return new NetworkBoundResource<List<RoutineVO>, List<RoutineTable>, PagedListModel<RoutineModel>>(executors,
                tables -> {
                    return tables.stream()
                            .map(table -> table.toVo(false))
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model -> model.toTable(0))
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model ->  model.toVO())
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
                return routineService.searchRoutines(string);
            }
        }.asLiveData();
    }


    public LiveData<Resource<List<RoutineVO>>> getFavouriteRoutines() {

        return new NetworkBoundResource<List<RoutineVO>, List<FavouriteRoutineTable>, PagedListModel<RoutineModel>>(executors,
                tables -> {
                    return tables.stream()
                            .map(table -> table.toVo())
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model -> model.toFavTable())
                            .collect(toList());
                },
                models -> {
                    return models.getResults().stream()
                            .map(model ->  model.toVO())
                            .collect(toList());
                }) {
            @Override //no me preocupo por esto, el getAllRoutines lo hace por mi
            protected void saveCallResult(@NonNull List<FavouriteRoutineTable> entities) {
                //saco las rutinas favoritas y las vuelvo a agregar, quizás me las favvearon
                database.favouriteRoutineDao().deleteAllFavourites();
                database.favouriteRoutineDao().insertFavourite(entities);
            }

            @Override //siempre hago fetch por si me cambiaron algo
            protected boolean shouldFetch(@Nullable List<FavouriteRoutineTable> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override //teóricamente, ya están cargadas en la base de datos local
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override //busco de mi base de datos para recuperar las que tengo marcadas
            protected LiveData<List<FavouriteRoutineTable>> loadFromDb() {
                return database.favouriteRoutineDao().getFavouriteRoutines();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return routineService.getFavouriteRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> addToFavourites(RoutineVO routine) {
        return new NetworkBoundResource<Void, Void, Void>(executors,
                table -> null,
                model -> null,
                model -> null
        ) {
            @Override
            protected void saveCallResult(@NonNull Void entity) {
                database.favouriteRoutineDao().addFavouriteRoutine(routine.toFavTable());
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return routineService.addToFavourites(routine.getId());
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> removeFromFavourites(RoutineVO routine) {
        return new NetworkBoundResource<Void, Void, Void>(executors, table -> null, model -> null, model -> null) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
                database.favouriteRoutineDao().deleteFavouriteRoutine(routine.toFavTable());
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return routineService.removeFromFavourites(routine.getId());
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
    //---------------------------- REVIEW REPOSITORY --------------------
    public LiveData<Resource<ReviewAnswerModel>> postReview( int routineId , int score){
        return new NetworkBoundResource<ReviewAnswerModel,Void, ReviewAnswerModel >( executors ,
                null , null , model -> model){
                @Override
                protected void saveCallResult(@NonNull Void entity) {
                }

                @Override
                protected boolean shouldFetch(@Nullable Void entity) {
                    return true;
                }

                @Override
                protected boolean shouldPersist(@Nullable ReviewAnswerModel model) {
                    return false;
                }

                @NonNull
                @Override
                protected LiveData<Void> loadFromDb() {
                    return AbsentLiveData.create();
                }

                @NonNull
                @Override
                protected LiveData<ApiResponse<ReviewAnswerModel>> createCall() {
                    return routineService.postReview( routineId , new ReviewModel(score , "Me dejo como el octa"));
                }
            }.asLiveData();
        }
        //------------------------------------ CATEGORY REPO--------------------
        public LiveData<Resource<List<CategoryVO>>> getCategories(){
           return new NetworkBoundResource<List<CategoryVO> , List<CategoryTable> , PagedListModel<CategoryModel> >(executors ,
                    modelEntity ->{
                        return modelEntity.stream().map( entity -> entity.toVO()).collect(toList());
                    }      ,
                   modelList -> {
                       return  modelList.getResults().stream().map(model -> model.toTable()).collect(toList());
                   } , modelList -> {
                     return modelList.getResults().stream().map( model -> model.toVo() ).collect(toList());
                    }    ){

               @Override
               protected void saveCallResult(@NonNull List<CategoryTable> entities) {
                   database.categoryDao().deleteAll();
                   database.categoryDao().insert(entities);
               }

               @Override
               protected boolean shouldFetch(@Nullable List<CategoryTable> entities) {
                   return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
               }

               @Override
               protected boolean shouldPersist(@Nullable PagedListModel<CategoryModel> model) {
                   return true;
               }

               @NonNull
               @Override
               protected LiveData<List<CategoryTable>> loadFromDb() {
                   return database.categoryDao().getAll();
               }

               @NonNull
               @Override
               protected LiveData<ApiResponse<PagedListModel<CategoryModel>>> createCall() {
                   return routineService.getCategories();
               }
           }.asLiveData();
        }

        //---------------------- Execute REPO

        public LiveData<Resource<ExecutionAnswerModel>> executeRoutine( int id ){
            return new NetworkBoundResource<ExecutionAnswerModel , Void , ExecutionAnswerModel>( executors,null , null , model -> model){
                @Override
                protected void saveCallResult(@NonNull Void entities) {
                }

                @Override
                protected boolean shouldFetch(@Nullable Void entities) {
                    return true;
                }

                @Override
                protected boolean shouldPersist(@Nullable ExecutionAnswerModel model) {
                    return false;
                }

                @NonNull
                @Override
                protected LiveData<Void> loadFromDb() {
                    return AbsentLiveData.create();
                }

                @NonNull
                @Override
                protected LiveData<ApiResponse<ExecutionAnswerModel>> createCall() {
                    return routineService.executeRoutine(id , new ExecutionModel( 20 , false ));
                }

            }.asLiveData();

        }


}
