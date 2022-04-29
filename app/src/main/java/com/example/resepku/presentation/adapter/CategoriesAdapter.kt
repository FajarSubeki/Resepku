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
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.resepku.R
import com.example.resepku.data.model.Category
import com.example.resepku.presentation.view.fragment.MainFragmentDirections

class CategoriesAdapter(
    val context: Context
) : ListAdapter<Category, CategoriesAdapter.CategoriesVH>(MealsListDiffUtil()) {

    private lateinit var navController: NavController

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoriesVH(view)
    }

    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
        val category = getItem(position)
        val categoryImageUrl = category.strCategoryThumb
        val categoryName = category.strCategory

        Glide.with(context).load(categoryImageUrl).into(holder.categoryImage)
        holder.categoryName.text = categoryName
        holder.itemView.setOnClickListener {
            initCategoryFragment(it, category)
        }
    }

    class CategoriesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView = itemView.findViewById(R.id.img_category)
        val categoryName: TextView = itemView.findViewById(R.id.txt_category)
    }

    private fun initCategoryFragment(view: View, category: Category) {
        navController = Navigation.findNavController(view)
        val action = MainFragmentDirections.actionMainFragmentToCategories2Fragment(category,null)
        navController.navigate(action)
    }

}

class MealsListDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}