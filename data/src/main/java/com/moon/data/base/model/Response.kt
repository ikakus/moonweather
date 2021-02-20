package com.moon.data.base.model

data class BaseResponse<T>(
    val data: T,
    val errorCode: Int?,
    val status: Boolean?,
    val timestamp: Int?
)
