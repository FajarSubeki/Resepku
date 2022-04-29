package com.example.resepku.data.response

import com.example.resepku.data.model.MealDetails
import com.google.gson.annotations.SerializedName

data class MealsDetailResponse(
    @SerializedName("meals")
    val mealsList: MutableList<MealDetails>
)
