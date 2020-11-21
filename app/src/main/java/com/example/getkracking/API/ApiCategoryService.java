package com.example.getkracking.API;

import androidx.lifecycle.LiveData;

import com.example.getkracking.API.model.CategoryModel;
import com.example.getkracking.API.model.PagedListModel;

import retrofit2.http.POST;

public interface ApiCategoryService {
    @POST("categories")
    LiveData<PagedListModel<CategoryModel>> getCategories();
}
