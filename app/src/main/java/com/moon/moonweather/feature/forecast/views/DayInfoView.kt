package com.moon.moonweather.feature.forecast.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.moon.moonweather.R
import com.moon.moonweather.feature.forecast.ForecastUiModel
import kotlinx.android.synthetic.main.day_info_view.view.*

class DayInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.day_info_view, this)
    }

    fun setData(it: ForecastUiModel) {
        if (it.text == null) {
            tv_forecast_text.visibility = View.GONE
            v_separator.visibility = View.GONE
        }
        if (it.peipsi == null) tv_peipsi.visibility = View.GONE
        tv_forecast_text.text = it.text
        tv_peipsi.text = it.peipsi
        tv_phenomenon.text = it.phenomenon
        tv_max_temp.text = "Max: ${it.max}"
        tv_min_temp.text = "Min: ${it.min}"
        iv_forecast_icon.setImageResource(it.drawableResource)
    }
}