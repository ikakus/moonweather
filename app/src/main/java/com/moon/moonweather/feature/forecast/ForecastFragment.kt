package com.moon.moonweather.feature.forecast

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.moon.moonweather.R
import com.moon.moonweather.componentprovider.forecast.ForecastComponentProvider
import com.moon.moonweather.core.base.BaseFragment
import com.moon.moonweather.feature.forecast.views.ForecastInfoView
import com.moon.moonweather.views.loading
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject


class ForecastFragment : BaseFragment<UiEvent, UiModel>(R.layout.fragment_forecast) {

    @Inject
    lateinit var forecastBindings: ForecastBindings
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ForecastComponentProvider.get().inject(this)
        forecastBindings.setup(this)
    }

    override fun accept(uiModel: UiModel) {
        uiModel.forecastDays?.let {
            pager.adapter = ForecastInfoAdapter(it)
            tab_layout.setupWithViewPager(pager)
        }

        loading(uiModel.loading)
    }

}

class ForecastInfoAdapter(private val forecastDays: List<ForecastDayUiModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return forecastDays.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ForecastInfoView(container.context)
        view.setData(forecastDays[position])
        container.addView(view)
        return view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return forecastDays[position].date
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }
}