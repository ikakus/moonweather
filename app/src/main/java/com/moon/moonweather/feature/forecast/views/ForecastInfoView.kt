package com.moon.moonweather.feature.forecast.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.moon.moonweather.R
import com.moon.moonweather.feature.forecast.ForecastDayUiModel
import kotlinx.android.synthetic.main.view_forecast.view.*

class ForecastInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_forecast, this)
    }

    fun setData(it: ForecastDayUiModel) {
        day_info_view_day.setData(it.day)
        day_info_view_night.setData(it.night)

        if (it.places == null) {
            rl_places_separator.visibility = GONE
            ll_places.visibility = GONE
        } else {
            rl_places_separator.visibility = VISIBLE
            ll_places.visibility = VISIBLE
        }

        it.places?.forEach { location ->
            ll_places.addView(PlaceInfoView(context).apply {
                setData(location)
            })
        }
    }
}