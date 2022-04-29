package com.example.resepku.presentation.datasource

import androidx.lifecycle.liveData
import com.example.resepku.data.base.Resource
import com.example.resepku.data.response.MealsDetailResponse
import com.example.resepku.domain.api.ServicesApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ActivityRetainedScoped
class MealDataSource @Inject constructor(private val apiService: ServicesApi) {

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()?.categories))
            }
        } catch (exception: Exception) {
            Resource.failed<MealsDetailResponse>(null, exception.message.toString())
        }
    }

    fun getMealOfCategory(categoryName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val mealsList = apiService.getMealsByCategory(categoryName).body()?.meals
            emit(Resource.success(mealsList))
        } catch (exception: Exception) {
            emit(Resource.failed(null, exception.message.toString()))
        }
    }

    fun getMealDetails(mealId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val meal=apiService.getMealDetails(mealId).body()
            emit(Resource.success(meal!!.mealsList))
        } catch (exception: Exception) {
            emit(Resource.failed(null, exception.message.toString()))
        }
    }
}