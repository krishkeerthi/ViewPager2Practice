package com.example.viewpager2

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
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
        // default tab mode fixed others are scroll, auto
        tabLayout.tabMode = TabLayout.MODE_AUTO

        // page change callback
        val pageChangeCallback = PageChangeCallback(requireActivity())

        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        if(context?.resources?.configuration?.orientation == 2){
            // unregister during landscape orientation
            viewPager.unregisterOnPageChangeCallback(pageChangeCallback)

        }

        // Page transformer
        viewPager.setPageTransformer(DepthPageTransformer())

        // vertical orientation
        //viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Layout direction
//        viewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
//        tabLayout.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL


        // Mediator
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Left"
                1 -> "Middle"
                2 -> "Right"
                3 -> "extra 1"
                4 -> "extra 2"
                5 -> "extra 3"
                else -> "Middle"
            }
        }.attach()

        // viewpager methods

        if(viewPager.currentItem == 0){
            //viewPager.setCurrentItem(4) // programmatic scrolling no dragging state
                // default scrolling is smooth
            viewPager.setCurrentItem(4, false) // directly goes to specified position
        }


    }

    class PageChangeCallback(val activity: Activity) : ViewPager2.OnPageChangeCallback() {

        // user performed scroll
        //onPageScrollStateChanged:        1             SCROLL_STATE_DRAGGING
        //onPageScrollStateChanged:        2             SCROLL_STATE_SETTLING
        //onPageSelected:              SELECTION
        //onPageScrollStateChanged:        0             SCROLL_STATE_IDLE
        //
        //PROGRAMATIC
        //onPageScrollStateChanged:        2             SCROLL_STATE_SETTLING
        //onPageSelected:              SELECTION
        //onPageScrollStateChanged:        0             SCROLL_STATE_IDLE

        override fun onPageSelected(position: Int) {
            //This method will be invoked when a new page becomes selected.
            Log.d(TAG, "onPageSelected: page selected: $position")
            Toast.makeText(activity, "Selected position: $position",
                Toast.LENGTH_SHORT).show()

        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //This method will be invoked when the current page is scrolled,
            // either as part of a
            // programmatically initiated smooth scroll
            // or a user initiated touch scroll.
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            Log.d(TAG, "onPageScrolled: position: $position positionOffset : $positionOffset " +
                    "positionOffsetPixels: $positionOffsetPixels ")
        }

        override fun onPageScrollStateChanged(state: Int) {

            //Called when the scroll state changes. Useful for discovering when the user
            // begins dragging, whxen a fake drag via programmatically is started, when the pager is automatically
            // settling to the current page, or when it is fully stopped/idle. state can be
            // one of SCROLL_STATE_IDLE =0, SCROLL_STATE_DRAGGING =1 or SCROLL_STATE_SETTLING= 2
            super.onPageScrollStateChanged(state)
            Log.d(TAG, "onPageScrollStateChanged: state: $state")
        }
    }

    class CollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 6
        }


        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> FragmentLeft()
                1 -> FragmentMiddle()
                2 -> FragmentRight()
                3 -> FragmentRight()
                4 -> FragmentRight()
                5 -> FragmentRight()
                else -> FragmentMiddle()
            }
        }
    }



    class DepthPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 0 -> { // [-1,0]
                        // Use the default slide transition when moving to the left page
                        alpha = 1f
                        translationX = 0f
                        translationZ = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> { // (0,1]
                        // Fade the page out.
                        alpha = 1 - position

                        // Counteract the default slide transition
                        translationX = pageWidth * -position
                        // Move it behind the left page
                        translationZ = -1f

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = (Companion.MIN_SCALE + (1 - Companion.MIN_SCALE) * (1 - Math.abs(position)))
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }

        companion object {
            private const val MIN_SCALE = 0.75f
        }
    }

}