package com.example.getkracking.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.getkracking.API.ApiResponse;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;
import com.example.getkracking.API.model.TokenModel;
import com.example.getkracking.API.model.UpdateUserModel;
import com.example.getkracking.API.model.UserModel;
import com.example.getkracking.entities.UserVO;
import com.example.getkracking.room.AppDatabase;
import com.example.getkracking.room.entities.RoutineTable;
import com.example.getkracking.room.entities.UserTable;
import com.example.getkracking.vo.AbsentLiveData;
import com.example.getkracking.vo.Resource;

import java.util.List;

import retrofit2.http.Body;


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
                //no debería borrar lo que ya hay, hago esto solo cuando modifico el user para evitar duplicados
                //database.userDao().deleteAll();
                //database.userDao().insert(table);
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
                return database.userDao().getUser();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return service.getCurrent();
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserVO>> updateUser(String fullName, String imageUrl) {
        return new NetworkBoundResource<UserVO, UserTable, UserModel>(executors,
                //Convierte UserTable a UserVO - llenar los campos que no vamos a usar
                table -> {
                    return new UserVO(table.id, false, true, table.username, table.fullName, "Other", table.email, table.image, "69420", 0, 1, 2);
                },
                //Convierte UserModel a UserTable
                model -> {
                    return new UserTable(model.getId(), model.getUsername(), model.getAvatarUrl(), model.getFullName(), model.getEmail());
                },
                //Convierte UserModel a UserVO
                model -> {
                    return new UserVO(model.getId(), false, true, model.getUsername(), model.getFullName(), "Other", model.getEmail(), model.getAvatarUrl(), "69420", 0, 1, 2);
                })
        {
            @Override
            protected void saveCallResult(@NonNull UserTable table) {
                //borro y reescribo para evitar duplicados
                database.userDao().deleteAll();
                database.userDao().insert(table);
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
            protected LiveData<UserTable> loadFromDb() {
                return database.userDao().getUser();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                //levanto de nuestra tabla
                UserTable currentUser = database.userDao().getUser().getValue();
                assert (currentUser != null); //FIXME: acá está llegando un null, y no entiendo por qué
                //hago el post con los placeholders apropiados
                UpdateUserModel updatedUser = new UpdateUserModel(
                        currentUser.id, currentUser.username, fullName, "Other", 2L, currentUser.email, "69420", imageUrl);
                return service.updateCurrent(updatedUser);
            }
        }.asLiveData();
    }
}
