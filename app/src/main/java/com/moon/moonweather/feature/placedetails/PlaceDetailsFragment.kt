package com.moon.moonweather.feature.placedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moon.moonweather.R


class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val name = args?.getSerializable(KEY)
        Toast.makeText(context, name as String, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY = "data"

        fun newInstance(foo: String) = PlaceDetailsFragment().apply {
            val args = Bundle()
            args.putSerializable(KEY, foo)
            arguments = args
        }
    }


}