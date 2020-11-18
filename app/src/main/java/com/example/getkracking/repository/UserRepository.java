package com.example.getkracking.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.API.model.TokenModel;
import com.example.getkracking.API.model.UserModel;
import com.example.getkracking.entities.UserVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.room.entities.UserTable;
import com.example.getkracking.vo.AbsentLiveData;
import com.example.getkracking.vo.Resource;

import java.util.List;


public class UserRepository {

    private AppExecutors executors;
    private ApiUserService service;
    private AppDatabase database;

    public UserRepository(AppExecutors executors, ApiUserService service, AppDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    public LiveData<Resource<String>> login(String username, String password) {

        return new NetworkBoundResource<String, Void, TokenModel>(executors,null, null, model -> model.getToken()) {

            @Override
            protected void saveCallResult(@NonNull Void entity) { }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable TokenModel model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TokenModel>> createCall() {
                return service.login(new CredentialsModel(username, password));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {

        return new NetworkBoundResource<Void, Void, Void>
                (executors, null, null, model -> model) {

            @Override
            protected void saveCallResult(@NonNull Void entity) { }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() { return AbsentLiveData.create(); }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserVO>> getCurrent(){
        return new NetworkBoundResource<UserVO, UserTable, UserModel>(executors,
            //Convierte UserTable a UserVO - llenar los campos que no vamos a usar
            table ->{
                return new UserVO(table.id, false, true, table.username, table.fullName, "Other", table.email, table.image, "69420", 0, 1, 2);
            },
            //Convierte UserModel a UserTable
            model ->{
                return new UserTable(model.getId(), model.getUsername(), model.getAvatarUrl(), model.getFullName(), model.getEmail());
            },
            //Convierte UserModel a UserVO
            model -> {
                return new UserVO(model.getId(), false, true, model.getUsername(), model.getFullName(), "Other", model.getEmail(), model.getAvatarUrl(), "69420", 0, 1, 2);
            })
        {
            @Override
            protected void saveCallResult(@NonNull UserTable table) {
                database.userDao().deleteAll();
                database.userDao().insert(table); //FIXME: H
            }

            @Override
            protected boolean shouldFetch(@Nullable UserTable table) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable UserModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserTable> loadFromDb(){
                return database.userDao().getUser().getValue().get(0);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return service.getCurrent();
            }
        }.asLiveData();
    }
}
