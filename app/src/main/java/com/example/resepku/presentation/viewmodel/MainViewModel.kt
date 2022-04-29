package com.example.resepku.presentation.viewmodel
import androidx.lifecycle.ViewModel
import com.example.resepku.presentation.datasource.MealDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val mealRepo : MealDataSource) : ViewModel() {

    fun getCategories() = mealRepo.getCategories()

    fun getMealsOfCategory(categoryName: String) = mealRepo.getMealOfCategory(categoryName)

    fun getMealDetails(mealId:String) = mealRepo.getMealDetails(mealId)
}