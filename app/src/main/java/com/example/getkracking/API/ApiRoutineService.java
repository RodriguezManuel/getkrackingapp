package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;

import retrofit2.http.GET;

public interface ApiRoutineService {
    @GET("routines")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getRoutines();
}
