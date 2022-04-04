package com.vylo.auth.signinfragment.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vylo.auth.signinfragment.domain.entity.response.SignInError

class Mapper(private val gson: Gson) {

    private inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

    fun fromApiErrorToSignInError(baseResponse: Any?): SignInError? {
        val json = gson.toJson(baseResponse)
        var data: SignInError? = null
        if (json != null)
            data = gson.fromJson(json, genericType<SignInError>())

        return data
    }

}