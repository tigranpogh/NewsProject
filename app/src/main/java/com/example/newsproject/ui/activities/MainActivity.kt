package com.example.newsproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsproject.R
import com.example.newsproject.databinding.ActivityMainBinding
import com.example.newsproject.ui.adapters.NewsFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater)
            .also { binding = it }
            .root)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            pager.adapter = NewsFragmentStateAdapter(this@MainActivity)
            binding.pager.isUserInputEnabled = false
            TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                val textRes = when (position) {
                    0 -> getString(R.string.search_news)
                    1 -> getString(R.string.favorite_news)
                    else -> {
                        throw IllegalArgumentException("Unknown tab id, can not provide tab name")
                    }
                }
                tab.text = textRes
            }.attach()
        }
    }
}