package com.moon.moonweather.feature.forecast.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.moon.moonweather.R
import com.moon.moonweather.feature.forecast.ShortLocationUiModel
import kotlinx.android.synthetic.main.place_info_view.view.*

class PlaceInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.place_info_view, this)
    }

    fun setData(it: ShortLocationUiModel) {
        tv_location_name.text = it.name
        tv_temp_range.text = "${it.min} - ${it.max}"
    }
}