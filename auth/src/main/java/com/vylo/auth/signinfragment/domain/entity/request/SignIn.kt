package com.vylo.auth.signinfragment.domain.entity.request

import com.google.gson.annotations.SerializedName

data class SignIn(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null
)
