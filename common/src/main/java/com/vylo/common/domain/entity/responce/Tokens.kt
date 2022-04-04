package com.vylo.common.domain.entity.responce

import com.google.gson.annotations.SerializedName

data class Tokens(
    @SerializedName("refresh")
    val refresh: String? = null,
    @SerializedName("access")
    val access: String? = null
)
