package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.ExerciseModel;
import com.example.getkracking.API.model.PagedListModel;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseService {
    @GET("/routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedListModel<ExerciseModel>>> getExercises(@Path("routineId") int routineId , @Path("cycleId") int cycleId);

}
