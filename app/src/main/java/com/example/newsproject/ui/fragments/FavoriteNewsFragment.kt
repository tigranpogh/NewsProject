package com.example.newsproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.newsproject.database.NewsModel
import com.example.newsproject.databinding.FragmentFavoriteNewsBinding
import com.example.newsproject.ui.viewmodels.NewsViewModel
import com.example.newsproject.ui.adapters.SavedNewsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteNewsFragment : Fragment() {

    private var _binding: FragmentFavoriteNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var savedNewsAdapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedNews()
        collectData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.savedNewsFlow.collect {savedNewsList ->
                if (savedNewsList.isNotEmpty()) {
                    initSavedNewsAdapter(savedNewsList)
                }
            }
        }
    }

    private fun initSavedNewsAdapter(items: List<NewsModel?>) {
        savedNewsAdapter = SavedNewsAdapter()
        binding.rvFavoriteNews.adapter = savedNewsAdapter
        savedNewsAdapter.setItems(items)
    }

}