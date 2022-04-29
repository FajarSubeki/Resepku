package com.example.resepku.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.resepku.R
import com.example.resepku.data.model.Category
import com.example.resepku.data.model.Meal
import com.example.resepku.presentation.view.fragment.CategoryMealFragmentDirections

class MealsAdapter(private val context: Context) :
    androidx.recyclerview.widget.ListAdapter<Meal, MealsAdapter.MealsVH>(MealListDiff()) {

    private lateinit var navController: NavController
    private lateinit var category: Category
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealsVH(view)
    }

    override fun onBindViewHolder(holder: MealsVH, position: Int) {
        val meal = getItem(position)
        val mealImageUrl = meal.strMealThumb
        val mealName = meal.strMeal

        holder.mealName.text = mealName
        Glide.with(context).load(mealImageUrl).into(holder.mealImage)

        holder.itemView.setOnClickListener {
            navController = Navigation.findNavController(it)
            val action = CategoryMealFragmentDirections.actionCategories2FragmentToDetailsFragment(meal.idMeal,category)
            navController.navigate(action)
        }
    }


    class MealsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ImageView = itemView.findViewById(R.id.img_meal)
        val mealName: TextView = itemView.findViewById(R.id.txt_meal)
    }
     fun navigate(category:Category){
        this.category=category
    }
}

class MealListDiff : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}