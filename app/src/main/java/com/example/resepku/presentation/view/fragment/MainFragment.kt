package com.example.resepku.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.resepku.data.base.Status
import com.example.resepku.data.base.showIf
import com.example.resepku.data.model.Category
import com.example.resepku.databinding.FragmentMainBinding
import com.example.resepku.presentation.adapter.CategoriesAdapter
import com.example.resepku.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var categoriesList: MutableList<Category>
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val mViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initVars() {

        categoriesList = mutableListOf()
        categoriesAdapter = CategoriesAdapter(requireContext())

//        binding.swipe.setOnRefreshListener {
//            getLatestMeals()
//            getCategories()
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        getCategories()
    }

    private fun getCategories() {

        mViewModel.getCategories().observe(viewLifecycleOwner) {
            binding.shimmerCategory.showIf {
                it.data == null
            }
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    categoriesAdapter.submitList(it.data)
                }
                Status.FAIL -> {
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        initCategoriesRecycler()
    }

    private fun initCategoriesRecycler() {
        binding.categoriesRecycler.apply {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoriesAdapter
        }
    }

}