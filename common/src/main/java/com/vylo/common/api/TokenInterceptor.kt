package com.vylo.common.api

import android.util.Log
import com.vylo.common.data.endpoint.BaseCallApi
import com.vylo.common.domain.entity.request.GetToken
import com.vylo.common.domain.entity.responce.Tokens
import com.vylo.common.util.SharedPreferenceData
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit

class TokenInterceptor(
    private val sharedPreferenceData: SharedPreferenceData
) : Interceptor {

    private val unauthorised = 401

    companion object {
        private lateinit var retrofit: Retrofit

        fun getRetrofit(retrofit: Retrofit) {
            this.retrofit = retrofit
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request: Request = chain.request()
        var newRequest: Request? = null

        val response = chain.proceed(request = request)
        if (!response.isSuccessful && response.code == unauthorised) {
            response.close()

            // API service to get refresh token
            val tokenService = retrofit.create(BaseCallApi::class.java)

            val apiToken: retrofit2.Response<Tokens> = tokenService.refreshTokenCall(
                GetToken(
                    sharedPreferenceData.retrieveRefreshToken
                )
            )

            if (apiToken.body() != null) {
                sharedPreferenceData.saveToken(apiToken.body()!!.access.toString())
                sharedPreferenceData.saveRefreshToken(apiToken.body()!!.refresh.toString())
            }

            sharedPreferenceData.retrieveToken?.let {
                newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $it")
                    .addHeader("X-CSRFToken", sharedPreferenceData.retrieveToken!!)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("accept", "application/json")
                    .build()
            }


            if (newRequest != null) chain.proceed(request = newRequest!!)
            else chain.proceed(request = request)

        } else {
            response
        }
    }
}