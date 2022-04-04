package com.vylo.common.api

import android.content.Context
import com.vylo.common.util.SharedPreferenceData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiBase {

    private const val TIME_OUT: Long = 300
    private lateinit var retrofit: Retrofit
    private var baseUrlString = ""

    fun updateBaseUrl(url: String, context: Context) {
        baseUrlString = url
        retrofit = getRetrofitObj(context)
    }

    fun getInstance(): Retrofit {
        TokenInterceptor.getRetrofit(retrofit)
        return retrofit
    }

    private fun getRetrofitObj(context: Context): Retrofit {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(NetworkConnectivityInterceptor(context))
        builder.addInterceptor(TokenInterceptor(SharedPreferenceData(context)))
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        builder.addInterceptor(interceptor)

        val client = builder.build()

        return Retrofit.Builder().baseUrl(baseUrlString)
            .client(client).addConverterFactory(GsonConverterFactory.create()).build().also { retrofit = it }
    }

}