package com.example.newsproject.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsproject.ui.fragments.FavoriteNewsFragment
import com.example.newsproject.ui.fragments.NewsSearchFragment

class NewsFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsSearchFragment()
            1 -> FavoriteNewsFragment()
            else -> {
                throw IllegalArgumentException("Can not provide a fragment for position: $position")
            }
        }
    }
}