package com.moon.moonweather.feature.forecast

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import com.moon.moonweather.R
import com.moon.moonweather.common.base.BaseFragment
import com.moon.moonweather.common.views.loading
import com.moon.moonweather.componentprovider.forecast.ForecastComponentProvider
import com.moon.moonweather.feature.forecast.views.ForecastInfoView
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.*
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
        GlobalScope.launch {
            uiEvents.send(UiEvent.PlaceClicked(placeName))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ForecastComponentProvider.get().inject(this)
        forecastBindings.setup(this, lifecycleScope)
        pager.adapter = adapter
        tab_layout.setupWithViewPager(pager)
    }


    override suspend fun emit(uiModel: UiModel) {
        uiModel.forecastDays?.let {

            // Ideally ModelWatcher should be used
            if ((adapter.forecastDays == it).not()) {
                adapter.forecastDays = it
                adapter.notifyDataSetChanged()
            }
        }

        loading(uiModel.loading)
    }

}

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
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }
}