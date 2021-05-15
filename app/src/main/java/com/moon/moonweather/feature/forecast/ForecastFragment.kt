package com.moon.moonweather.feature.forecast

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.moon.moonweather.R
import com.moon.moonweather.common.base.BaseFragment
import com.moon.moonweather.componentprovider.forecast.ForecastComponentProvider
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class ForecastFragment : BaseFragment<UiEvent, UiModel>(R.layout.fragment_forecast) {

    @Inject
    lateinit var forecastBindings: ForecastBindings

    var adapter = ForecastInfoAdapter { placeName ->
        sendEvent(placeName)
    }

    private fun sendEvent(placeName: String) {
        lifecycleScope.launch {
            uiEvents.send(UiEvent.PlaceClicked(placeName))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ForecastComponentProvider.get().inject(this)
        pager.adapter = adapter
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                lifecycleScope.launch {
                    val day = adapter.forecastDays[position]
                    uiEvents.send(UiEvent.PagerDaySelected(day))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        tab_layout.setupWithViewPager(pager)
        forecastBindings.setup(this)
    }

    override suspend fun emit(uiModel: UiModel) {
        uiModel.forecastDays?.let {

            // Ideally ModelWatcher should be used
            if ((adapter.forecastDays == it).not()) {
                adapter.forecastDays = it
                adapter.notifyDataSetChanged()
            }

            uiModel.selectedDay?.let { day ->
                val position = adapter.getItemPosition(day)
                pager.currentItem = position
            }
        }

        loading_verlay.show(uiModel.loading)
    }

}

private fun View.show(show: Boolean) {
    visibility = if(show){
        View.VISIBLE
    }else{
        View.GONE
    }
}
