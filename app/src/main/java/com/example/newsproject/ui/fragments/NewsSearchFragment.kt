package com.example.newsproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.newsproject.data.ArticlesItem
import com.example.newsproject.database.NewsModel
import com.example.newsproject.databinding.FragmentNewsSearchBinding
import com.example.newsproject.ui.adapters.NewsAdapter
import com.example.newsproject.ui.viewmodels.NewsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSearchFragment : Fragment() {

    private var _binding: FragmentNewsSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getNews(it) }
                binding.progressBar.visibility = VISIBLE
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.searchView.setOnClickListener {
            viewModel.getNews(binding.searchView.query.toString())
            binding.progressBar.visibility = VISIBLE
            hideKeyboard()
        }
    }

    private fun initNewsAdapter(items: List<ArticlesItem?>) {
        newsAdapter = NewsAdapter {article ->
            val clickedArticle = NewsModel(article.title.toString(), article.description.toString(),
                article.url.toString(), article.publishedAt.toString())
            viewModel.saveArticle(clickedArticle)
            Toast.makeText(requireContext(), "Saved to favorites", Toast.LENGTH_SHORT).show()
        }
        binding.rvNews.adapter = newsAdapter
        newsAdapter.setItems(items)
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.newsFlow.collect { data ->
                if (!data.isNullOrEmpty()) {
                    initNewsAdapter(data)
                }
                binding.progressBar.visibility = GONE
            }
        }

        lifecycleScope.launch {
            viewModel.errorFlow.collect {
                if (it) {
                    binding.progressBar.visibility = GONE
                }
            }
        }
    }

    fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }
}