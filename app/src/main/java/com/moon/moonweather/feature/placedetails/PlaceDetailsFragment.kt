package com.moon.moonweather.feature.placedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.moonweather.R
import com.moon.moonweather.feature.forecast.ForecastUiModel
import com.moon.moonweather.feature.forecast.utils.DegreesToHuman
import com.moon.moonweather.feature.forecast.utils.PhenomenonToDrawable
import kotlinx.android.synthetic.main.fragment_place_details.*


class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments

        val day = args?.getSerializable(KEY_DAY) as PlaceDomainModel
        val night = args.getSerializable(KEY_NIGHT) as PlaceDomainModel

        val tempFormatter = DegreesToHuman()
        val phMapper = PhenomenonToDrawable()

        val max = (day.tempmax ?: night.tempmax)
        val min = (day.tempmin ?: night.tempmin)

        tv_place_name.text = day.name
        cv_place_day_info.setData(
            ForecastUiModel(
                phenomenon = day.phenomenon,
                min = tempFormatter.getStringWithPostfix(min.toString()),
                max = tempFormatter.getStringWithPostfix(max.toString()),
                drawableResource = phMapper.dayToDrawable(day.phenomenon)
            )
        )

    }

    companion object {
        const val KEY_DAY = "data_day"
        const val KEY_NIGHT = "data_night"

        fun newInstance(
            day: PlaceDomainModel?,
            night: PlaceDomainModel?
        ) = PlaceDetailsFragment().apply {
            val args = Bundle()
            args.putSerializable(KEY_DAY, day)
            args.putSerializable(KEY_NIGHT, night)
            arguments = args
        }
    }


}