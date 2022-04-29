package com.example.resepku.domain.api

import com.example.resepku.data.response.CategoryResponse
import com.example.resepku.data.response.MealsDetailResponse
import com.example.resepku.data.response.MealsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicesApi {

    @GET("random.php")
    suspend fun getLatestMeals(): Response<MealsDetailResponse>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): Response<MealsResponse>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId:String) : Response<MealsDetailResponse>

}