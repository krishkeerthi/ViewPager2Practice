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

class FragmentRight : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Fragment right")
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: Fragment right")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment right")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Fragment right")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: Fragment right")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: Fragment right")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: Fragment right")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: Fragment right")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: Fragment right")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: Fragment right")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Fragment right")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: Fragment right")
        super.onDetach()
    }
}