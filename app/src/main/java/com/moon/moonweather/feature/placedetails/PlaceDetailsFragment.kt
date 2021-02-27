package com.moon.moonweather.feature.placedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.moonweather.R


class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val day = args?.getSerializable(KEY_DAY)
        val night = args?.getSerializable(KEY_NIGHT)
        Toast.makeText(context, day.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_DAY = "data_day"
        const val KEY_NIGHT = "data_night"

        fun newInstance(day: PlaceDomainModel?, night: PlaceDomainModel?) =
            PlaceDetailsFragment().apply {
                val args = Bundle()
                args.putSerializable(KEY_DAY, day)
                args.putSerializable(KEY_NIGHT, night)
                arguments = args
            }
    }


}