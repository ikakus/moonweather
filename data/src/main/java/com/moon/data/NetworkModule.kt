package com.moon.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_HOSTNAME = "https://weather.aw.ee/api/estonia/"

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .baseUrl(API_HOSTNAME)
            .client(client)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): ForecastApi {
        return retrofit.create(ForecastApi::class.java)
    }

}