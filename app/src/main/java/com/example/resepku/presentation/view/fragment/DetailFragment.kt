package com.example.resepku.presentation.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.resepku.R
import com.example.resepku.data.base.Status
import com.example.resepku.data.model.MealDetails
import com.example.resepku.databinding.FragmentDetailsBinding
import com.example.resepku.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var navController: NavController
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(args.category != null){
                val action = DetailFragmentDirections.actionDetailsFragmentToCategories2Fragment(args.category,null)
                navController.navigate(action)
            }else{
                val action = DetailFragmentDirections.actionDetailsFragmentToMainFragment()
                navController.navigate(action)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        val mealId = args.mealId
        getMealDetails(mealId)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        //handle color toolbar when scroll up
        binding.collapseToolbar.setExpandedTitleColor(Color.argb(0, 0, 0, 0))
        binding.collapseToolbar.setContentScrimColor(resources.getColor(R.color.white))
        //added icon to navigation
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back)

        val mealId = args.mealId
        binding.swLayout.setOnRefreshListener {
            getMealDetails(mealId)
        }

    }

    private fun getMealDetails(mealId: String) {
        mainViewModel.getMealDetails(mealId).observe(viewLifecycleOwner) {
            val meals = it.data
            when (it.status) {
                Status.LOADING -> {
                    binding.swLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    binding.swLayout.isRefreshing = false
                    val meal= meals!![0]
                    setupVariables(meal)
                    openYoutube(meal)
                }
                Status.FAIL -> {
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun putMealImage(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(binding.ivMeal)
        Glide.with(this).load(imageUrl).into(binding.ivBgMeal)
    }

    private fun putMealText(mealName: String) {
        binding.tvMeal.text = mealName
    }

    private fun putInstructions(instructionText: String) {
        binding.jtvInstructions.text = instructionText
    }

    @SuppressLint("SetTextI18n")
    private fun putCategoryName(categoryName: String, countryName: String) {
        binding.tvSubMeal.text = "$categoryName | $countryName"
    }

    private fun putIngredient(gradients: String) {
        binding.tvIngredients.text = gradients
    }

    private fun getIngredient(ingredients: MutableList<String>) {
        val ingredientText = StringBuilder()
        for (ingredient: String in ingredients) {
            if (ingredient != " " && ingredient.isNotEmpty()) {
                ingredientText.append("\n \u2022$ingredient")
            }
            putIngredient(ingredientText.toString())
        }
    }

    private fun addIngredientToIngredients(meal: MealDetails) {
        val ingredients = mutableListOf<String>()
        ingredients.add(meal.strIngredient1)
        ingredients.add(meal.strIngredient2)
        ingredients.add(meal.strIngredient3)
        ingredients.add(meal.strIngredient4)
        ingredients.add(meal.strIngredient5)
        ingredients.add(meal.strIngredient6)
        ingredients.add(meal.strIngredient7)
        ingredients.add(meal.strIngredient8)
        ingredients.add(meal.strIngredient9)
        ingredients.add(meal.strIngredient10)
        ingredients.add(meal.strIngredient11)
        ingredients.add(meal.strIngredient12)
        ingredients.add(meal.strIngredient13)
        ingredients.add(meal.strIngredient14)
        ingredients.add(meal.strIngredient15)
        ingredients.add(meal.strIngredient16)
        ingredients.add(meal.strIngredient17)
        ingredients.add(meal.strIngredient18)
        ingredients.add(meal.strIngredient19)
        ingredients.add(meal.strIngredient20)

        getIngredient(ingredients)
    }

    private fun addMeasures(meal: MealDetails) {
        val measures = mutableListOf<String>()
        measures.add(meal.strMeasure1)
        measures.add(meal.strMeasure2)
        measures.add(meal.strMeasure3)
        measures.add(meal.strMeasure4)
        measures.add(meal.strMeasure5)
        measures.add(meal.strMeasure6)
        measures.add(meal.strMeasure7)
        measures.add(meal.strMeasure8)
        measures.add(meal.strMeasure9)
        measures.add(meal.strMeasure10)
        measures.add(meal.strMeasure11)
        measures.add(meal.strMeasure12)
        measures.add(meal.strMeasure13)
        measures.add(meal.strMeasure14)
        measures.add(meal.strMeasure15)
        measures.add(meal.strMeasure16)
        measures.add(meal.strMeasure17)
        measures.add(meal.strMeasure18)
        measures.add(meal.strMeasure19)
        measures.add(meal.strMeasure20)

        getMeasures(measures)
    }

    private fun getMeasures(measuresList: MutableList<String>) {
        val measureText = StringBuilder()
        for (measure: String in measuresList) {
            if (measure != " " && measure.isNotEmpty()) {
                Log.d(TAG, "getMeasures: $measure")
                measureText.append("\n \u2022$measure")
            }
            putMeasuresText(measureText.toString())
        }
    }

    private fun putMeasuresText(measureText: String) {
        binding.tvMeasures.text = measureText
    }

    private fun setupVariables(meal: MealDetails) {
        val imageUrl = meal.strMealThumb
        putMealImage(imageUrl)
        val mealName = meal.strMeal
        putMealText(mealName)
        val instructionText = meal.strInstructions
        putInstructions(instructionText)
        val categoryName = meal.strCategory
        val countryName = meal.strArea
        putCategoryName(categoryName, countryName)

        addIngredientToIngredients(meal)
        addMeasures(meal)
    }

    private fun openYoutube(meal: MealDetails) {
        binding.llYoutube.setOnClickListener {
            implicitIntent(meal.strYoutube)
        }
    }

    private fun implicitIntent(uri: String) {
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity?.startActivity(implicitIntent)
    }


    companion object {
        private const val TAG = "DetailsFragment"
    }

}