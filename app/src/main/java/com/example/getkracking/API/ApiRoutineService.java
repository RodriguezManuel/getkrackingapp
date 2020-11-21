package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.CategoryModel;
import com.example.getkracking.API.model.ExecutionAnswerModel;
import com.example.getkracking.API.model.ExecutionModel;
import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.ReviewAnswerModel;
import com.example.getkracking.API.model.ReviewModel;
import com.example.getkracking.API.model.RoutineModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRoutineService {

    @GET("categories?size=999999")
    LiveData<ApiResponse<PagedListModel<CategoryModel>>> getCategories();
    @POST("routines/{routineId}/execute")
    LiveData<ApiResponse<ExecutionAnswerModel>> executeRoutine(@Path("routineId") int routineId , @Body ExecutionModel executionModel);

    @POST("routines/{routineId}/ratings")
    LiveData<ApiResponse<ReviewAnswerModel>> postReview(@Path("routineId") int routineId , @Body ReviewModel reviewModel);

    @GET("routines?size=999999")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getRoutines();

    @GET("routines?size=999999&search={string}")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> searchRoutines(@Path("string") String string);

    @GET("user/current/routines/favourites?size=999999")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getFavouriteRoutines();

    @GET("routines/{routineId}")
    LiveData<ApiResponse<RoutineModel>> getRoutineById(@Path("routineId") int routineId);

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> addToFavourites(@Path("routineId") int routineId);

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> removeFromFavourites(@Path("routineId") int routineId);
}
