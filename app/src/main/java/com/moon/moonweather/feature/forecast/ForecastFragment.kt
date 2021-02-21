package com.moon.moonweather.feature.forecast

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.moon.moonweather.R
import com.moon.moonweather.componentprovider.forecast.ForecastComponentProvider
import com.moon.moonweather.core.base.BaseFragment
import javax.inject.Inject

class ForecastFragment : BaseFragment<UiEvent, UiModel>(R.layout.fragment_forecast) {

    @Inject
    lateinit var forecastBindings: ForecastBindings
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ForecastComponentProvider.get().inject(this)
        forecastBindings.setup(this)
    }

    override fun accept(t: UiModel) {
        Toast.makeText(requireContext(), t.text, Toast.LENGTH_SHORT).show()

    }
}