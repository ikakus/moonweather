package com.moon.data

import com.moon.data.base.model.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("weatherUrl")
    fun get4DaysForecast(@Body body: Any): Single<BaseResponse<String>>
}