package com.vylo.auth.signinfragment.domain.entity.response

import com.google.gson.annotations.SerializedName

data class SignInError(
    @SerializedName("detail")
    val detail: String? = null
)
