package com.vylo.common

import androidx.lifecycle.ViewModel
import com.vylo.common.util.SharedPreferenceData
import com.vylo.common.util.enums.ScreenType

open class BaseViewModel(
    private val sharedPreferenceData: SharedPreferenceData,
) : ViewModel() {

    val getScreenType get() = sharedPreferenceData.retrieveScreenType

    fun setScreenType(screenType: ScreenType) {
        sharedPreferenceData.saveScreenType(screenType)
    }

}