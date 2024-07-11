package com.example.furnitureshop.com.example.furnitureshop.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furnitureshop.R
import com.example.furnitureshop.com.example.furnitureshop.adapters.HomeViewPagerAdapter
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.AccessoryFragment
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.ChairFragment
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.CupboardFragment
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.FurnitureFragment
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.MainCategoryFragment
import com.example.furnitureshop.com.example.furnitureshop.fragments.categories.TableFragment
import com.example.furnitureshop.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

        val viewPager2Adapter =
            HomeViewPagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chairs"
                2 -> tab.text = "Cupboards"
                3 -> tab.text = "Tables"
                4 -> tab.text = "Accessories"
                5 -> tab.text = "Furniture"
            }
        }.attach()

    }
}
