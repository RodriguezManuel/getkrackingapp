package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRoutineService {
    @GET("routines")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getRoutines();

    @GET("/user/current/routines/favourites")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getFavouriteRoutines();

    @POST("/user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> addToFavourites(int routineId);

    @DELETE("/user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> removeFromFavourites(int routineId);
}
