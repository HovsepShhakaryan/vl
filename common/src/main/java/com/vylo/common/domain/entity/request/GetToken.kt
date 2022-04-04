package com.vylo.common.domain.entity.request

import com.google.gson.annotations.SerializedName

data class GetToken(
    @SerializedName("refresh")
    val refresh: String? = null
)
