package com.vylo.auth.signinfragment.domain.usecase

import com.vylo.auth.signinfragment.domain.entity.response.SignInError

interface SignInMapperUseCase {
    fun getApiErrorMessage(baseResponse: Any?): SignInError?
}