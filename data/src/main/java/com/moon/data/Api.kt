package com.moon.data

import com.moon.data.base.model.BaseResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("weatherUrl")
    fun login(@Body body: Any): Single<BaseResponse<String>>
}