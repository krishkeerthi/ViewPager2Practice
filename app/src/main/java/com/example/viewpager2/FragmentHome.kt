package com.example.viewpager2

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
        // Viewpager swipes use transaction add,

        val tabLayout = binding.tabLayout
        // default tab mode fixed others are scroll, auto
        tabLayout.tabMode = TabLayout.MODE_AUTO

        // page change callback
        val pageChangeCallback = PageChangeCallback(requireActivity())

        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        if(context?.resources?.configuration?.orientation == 2){
            // unregister during landscape orientation
            viewPager.unregisterOnPageChangeCallback(pageChangeCallback)

            viewPager.isUserInputEnabled = false
            // if false set -> then dragging not possible, but clicking the tabs is possible to switch
        }

        // off screen page limit

        Toast.makeText(requireContext(), "Offscreen page limit: ${viewPager.offscreenPageLimit}", Toast.LENGTH_SHORT).show()
        //Log.d(TAG, "onViewCreated: offscreen ${ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT}")
        // by default offScreenPagelimit is -1 i.e ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        // offscreenPageLimit cant be 0

        // ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        // calculate views efficiently and detaches when out of screen and reattaches when came back
        viewPager.offscreenPageLimit = 1
        // this prepares the adjacent pages to start state, when the page is reached it just resumes,
        // those invisible tabs are not destroyed(both sides) like OFFSCREEN_PAGE_LIMIT_DEFAULT.

        // Page transformer
        viewPager.setPageTransformer(DepthPageTransformer())

        // vertical orientation
        //viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Layout direction
//        viewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
//        tabLayout.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL


        // recyclerview item decoration
        val itemDecoration = object: RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                Log.d(TAG, "onDraw: item decoration ")
            }
        }
        val itemDecoration1 = object: RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                Log.d(TAG, "onDraw: item decoration 1")
            }
        }
        viewPager.addItemDecoration(itemDecoration)
        viewPager.addItemDecoration(itemDecoration1, 1) //index – Position in the decoration
        // chain to insert this decoration at.

        viewPager.removeItemDecorationAt(1)
        viewPager.removeItemDecoration(itemDecoration)

        viewPager.invalidateItemDecorations()
        // not understood
        //Invalidates all ItemDecorations. If ViewPager2 has item decorations,
        // calling this method will trigger a requestLayout() call.

//        Log.d(TAG, "onViewCreated: item decoration at 0 : ${viewPager.getItemDecorationAt(0)}")
//        Log.d(TAG, "onViewCreated: item decoration at 1 : ${viewPager.getItemDecorationAt(1)}")

        Log.d(TAG, "onViewCreated: item decoration count ${viewPager.itemDecorationCount}")

        // scrolling orientation
        Log.d(TAG, "onViewCreated: negative scrolling: ${viewPager.canScrollHorizontally(-1) }")
        Log.d(TAG, "onViewCreated: positive scrolling: ${viewPager.canScrollHorizontally(1) }")
        // checks whether scrolling in the given direction possible
        // direction – Negative to check scrolling left, positive to check scrolling right.
        //true if this view can be scrolled in the specified direction, false otherwise.

        //negative = up , positive = down
        Log.d(TAG, "onViewCreated: negative scrolling: ${viewPager.canScrollVertically(-1) }")
        Log.d(TAG, "onViewCreated: positive scrolling: ${viewPager.canScrollVertically(1)}")

        // fake drag
//        if(!viewPager.isFakeDragging)
//            viewPager.beginFakeDrag()
//        viewPager.fakeDragBy(50F)
//        viewPager.endFakeDrag()
//        viewPager.isFakeDragging


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

//        if(viewPager.currentItem == 0){
//            //viewPager.setCurrentItem(4) // programmatic scrolling no dragging state
//                // default scrolling is smooth
//            //viewPager.setCurrentItem(4, true) // directly goes to specified position
//            // Important: On smooth scroll all fragment involved in scrolling is called
//            // smoothscroll false: only the specific page(fragment) lifecycle is called
//        }


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
                3 -> FragmentExtra()
                4 -> FragmentExtra()
                5 -> FragmentExtra()
                else -> FragmentExtra()
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