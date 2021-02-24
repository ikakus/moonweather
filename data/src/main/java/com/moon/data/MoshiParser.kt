package com.moon.data

import com.moon.data.forecast.model.ForecastListEntity
import com.moon.domain.forecast.model.ForecastListDomainModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class MoshiParser {
    private var moshi = Moshi.Builder().build()

    fun parseFrom(json: String): ForecastListDomainModel? {

        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<ForecastListEntity> =
            moshi.adapter(ForecastListEntity::class.java)

        return jsonAdapter.fromJson(json)
    }

    fun parseTo(data: ForecastListDomainModel): String {
        val jsonAdapter: JsonAdapter<ForecastListDomainModel> =
            moshi.adapter<ForecastListDomainModel>(Any::class.java)
        return jsonAdapter.toJson(data)
    }
}

