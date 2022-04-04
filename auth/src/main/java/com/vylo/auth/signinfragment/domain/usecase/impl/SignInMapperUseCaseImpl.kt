package com.vylo.auth.signinfragment.domain.usecase.impl

import com.vylo.auth.signinfragment.data.Mapper
import com.vylo.auth.signinfragment.domain.usecase.SignInMapperUseCase

class SignInMapperUseCaseImpl(
    private val mapper: Mapper
) : SignInMapperUseCase {

    override fun getApiErrorMessage(baseResponse: Any?) = mapper.fromApiErrorToSignInError(baseResponse)
}