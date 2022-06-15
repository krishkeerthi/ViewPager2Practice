package com.example.viewpager2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager2.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        val collectionAdapter = CollectionAdapter(this)

        val viewPager = binding.viewPager2
        viewPager.adapter = collectionAdapter

        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Left"
                1 -> "Middle"
                2 -> "Right"
                else -> "Middle"
            }
        }.attach()
    }

    class CollectionAdapter(fragment: Fragment ): FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> FragmentLeft()
                1 -> FragmentMiddle()
                2 -> FragmentRight()
                else -> FragmentMiddle()
            }
        }
    }
}