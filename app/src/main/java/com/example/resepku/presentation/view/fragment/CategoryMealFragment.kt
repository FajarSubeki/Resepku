package com.example.resepku.presentation.view.fragment

import android.os.Bundle
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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.resepku.data.base.Status
import com.example.resepku.data.model.Category
import com.example.resepku.databinding.FragmentMealCategoryBinding
import com.example.resepku.presentation.adapter.MealsAdapter
import com.example.resepku.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealFragment : Fragment(){

    private val categories2FragmentArgs: CategoryMealFragmentArgs by navArgs()
    lateinit var category: Category
    private lateinit var categoriesList: List<Category>
    private lateinit var mealsAdapter: MealsAdapter
    private var _binding: FragmentMealCategoryBinding? = null
    private val binding
        get() = _binding!!

    lateinit var navController: NavController

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealsAdapter = MealsAdapter(requireContext())

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action= CategoryMealFragmentDirections.actionCategories2FragmentToMainFragment()
            navController.navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        category = categories2FragmentArgs.category!!
        mealsAdapter.navigate(category)
        getCategories()
        initVars()
    }

    private fun getCategories() {
        mainViewModel.getCategories().observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                categoriesList = it.data!!
            }
        }
    }

    private fun initVars() {
        getCategoryDesc()
    }

    private fun getCategoryDesc() {
        val categoryName = category.strCategory
        binding.toolbarMenu.tvTitle.text = categoryName
        getMealsByCategoryName(categoryName)
    }

    private fun getMealsByCategoryName(categoryName: String) {
        mainViewModel.getMealsOfCategory(categoryName).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerCategoriesMeal.startShimmer()
                }
                Status.SUCCESS -> {
                    val mealsList=it.data
                    mealsAdapter.submitList(mealsList)
                    binding.shimmerCategoriesMeal.visibility = View.GONE
                }
                Status.FAIL -> {
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        setupMealsContainer()
    }

    private fun setupMealsContainer() {
        binding.rvMealCategory.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = mealsAdapter
        }
    }
}