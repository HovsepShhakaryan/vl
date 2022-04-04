package com.vylo.artkai

import android.content.Context

interface ContextProvider {
    fun getActivityContext(): Context?
}
