package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.PagedList;
import com.example.getkracking.API.model.Sport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSportsService {
    @GET("sports")
    LiveData<ApiResponse<PagedList<Sport>>> getSports();

    @GET("sports")
    LiveData<ApiResponse<Sport>> addSport(@Body Sport sport);

    @GET("sports/{sportId}")
    LiveData<ApiResponse<Sport>> getSport(@Path("sportId") int sport);

    @PUT("sports/{sportId}")
    LiveData<ApiResponse<Sport>> modifySport(@Path("sportId") int sportId , @Body Sport sport);

    @DELETE("sports/{sportId}")
    LiveData<ApiResponse<Void>> deleteSport(@Path("sportId") int sportId);


}
