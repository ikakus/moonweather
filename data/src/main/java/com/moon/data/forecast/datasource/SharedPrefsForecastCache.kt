package com.moon.data.forecast.datasource

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.moon.data.MoshiParser
import com.moon.data.base.Cache
import com.moon.domain.forecast.model.ForecastListDomainModel

private const val KEY_FORECAST = "key_forecast"
private const val PREFS_FORECAST = "prefs_forecast"

class SharedPrefsForecastCache(private val context: Context) : Cache<ForecastListDomainModel> {

    private val parser = MoshiParser()

    override fun put(data: ForecastListDomainModel) {
        val a = parser.parseTo(data)
        context.getSharedPreferences(PREFS_FORECAST, MODE_PRIVATE)
            .edit()
            .putString(KEY_FORECAST, a)
            .apply()
    }

    override fun get(): ForecastListDomainModel? {
        val json = context.getSharedPreferences(PREFS_FORECAST, MODE_PRIVATE)
            .getString(KEY_FORECAST, null) ?: return null
        return parser.parseFrom(json)
    }

}

