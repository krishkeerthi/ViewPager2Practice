package com.example.viewpager2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentExtra : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Fragment extra")
        return inflater.inflate(R.layout.fragment_extra, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: Fragment extra")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment extra")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Fragment extra")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: Fragment extra")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: Fragment extra")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: Fragment extra")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: Fragment extra")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: Fragment extra")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: Fragment extra")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Fragment extra")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: Fragment extra")
        super.onDetach()
    }
}