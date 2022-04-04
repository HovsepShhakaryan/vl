package com.vylo.common.domain

import com.vylo.common.BaseViewModel
import com.vylo.common.api.ApiBase
import com.vylo.common.util.SharedPreferenceData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single { BaseViewModel(SharedPreferenceData(androidContext())) }
    single { ApiBase }
}