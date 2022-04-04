package com.vylo.auth.signinfragment.data.repository.impl

import com.vylo.auth.signinfragment.data.endpoint.SignInApi
import com.vylo.auth.signinfragment.data.repository.SignInRepository
import com.vylo.auth.signinfragment.domain.entity.request.SignIn
import com.vylo.common.api.ApiBase
import com.vylo.common.data.repository.BaseRepo
import org.koin.java.KoinJavaComponent

class SignInRepositoryImpl : BaseRepo(), SignInRepository {

    private val apiBase: ApiBase by KoinJavaComponent.inject(ApiBase::class.java)
    private val client = apiBase.getInstance().create(SignInApi::class.java)

    override suspend fun postSignInCall(signIn: SignIn) = safeApiCall { client.signInCall(signIn) }


}