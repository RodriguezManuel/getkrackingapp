package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.PagedListModel;
import com.example.getkracking.API.model.SportModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSportService {
    @GET("sports")
    LiveData<ApiResponse<PagedListModel<SportModel>>> getSports();

    @GET("sports")
    LiveData<ApiResponse<SportModel>> addSport(@Body SportModel sportModel);

    @GET("sports/{sportId}")
    LiveData<ApiResponse<SportModel>> getSport(@Path("sportId") int sport);

    @PUT("sports/{sportId}")
    LiveData<ApiResponse<SportModel>> modifySport(@Path("sportId") int sportId , @Body SportModel sportModel);

    @DELETE("sports/{sportId}")
    LiveData<ApiResponse<Void>> deleteSport(@Path("sportId") int sportId);


}
