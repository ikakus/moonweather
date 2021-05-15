package com.moon.moonweather.feature.forecast

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.moon.moonweather.feature.forecast.views.ForecastInfoView

class ForecastInfoAdapter(
    var forecastDays: List<ForecastDayUiModel> = emptyList(),
    private val clickListener: ((String) -> Unit?)
) : PagerAdapter() {
    override fun getCount(): Int {
        return forecastDays.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ForecastInfoView(container.context).apply {
            setData(forecastDays[position])
            this.placeClickListener = clickListener
        }
        container.addView(view)
        return view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return forecastDays[position].dateTitle
    }

    override fun getItemPosition(obj: Any): Int {
        obj as ForecastDayUiModel
        return forecastDays.indexOf(obj)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }
}