package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.CycleModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.RoutineModel;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCycleService {
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedListModel<CycleModel>>> getCycles(@Path("routineId") int id);
}
