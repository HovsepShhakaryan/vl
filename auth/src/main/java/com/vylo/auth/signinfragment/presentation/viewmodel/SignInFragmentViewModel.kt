package com.vylo.auth.signinfragment.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vylo.auth.R
import com.vylo.auth.signinfragment.domain.usecase.impl.AuthValidationUseCaseImpl
import com.vylo.auth.signinfragment.domain.usecase.impl.SignInMapperUseCaseImpl
import com.vylo.auth.signinfragment.domain.usecase.impl.SignInUseCaseImpl
import com.vylo.common.api.Resource
import com.vylo.common.util.Converter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInFragmentViewModel(
    private val signInUseCaseImpl: SignInUseCaseImpl,
    private val authValidationUseCaseImpl: AuthValidationUseCaseImpl,
    private val signInMapperUseCaseImpl: SignInMapperUseCaseImpl,
    application: Application
) : AndroidViewModel(application) {

    private val context = application
    val responseError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val responseSuccess: MutableLiveData<String> by lazy { MutableLiveData() }

    fun signInCall() {
        viewModelScope.launch {
            val signInRequestObject = authValidationUseCaseImpl.requestSignInModel()
            when (val signInResponse = signInUseCaseImpl.invokeSignIn(signInRequestObject)) {
                is Resource.Success -> {
                    signInResponse.data?.refresh.let { signInUseCaseImpl.saveRefreshToken(signInResponse.data?.refresh!!) }
                    signInResponse.data?.access.let { signInUseCaseImpl.saveToken(signInResponse.data?.access!!) }
                    responseSuccess.postValue(context.getString(R.string.label_success_sign_in))
                }
                is Resource.ApiError -> {
                    val apiErrorMessage = signInMapperUseCaseImpl.getApiErrorMessage(signInResponse.errorData)
                    apiErrorMessage?.detail.let { responseError.postValue(apiErrorMessage!!.detail) }
                }
                is Resource.Error -> responseError.postValue(signInResponse.errorMessage)
            }
        }
    }

    fun checkEmailAddressIsEmpty(inputTypeValue: String) =
        authValidationUseCaseImpl.checkEmailAddressIsEmpty(inputTypeValue)

    fun checkPasswordIsEmpty(inputTypeValue: String) =
        authValidationUseCaseImpl.checkPasswordIsEmpty(inputTypeValue)

    fun checkEmailAddressIsValid(inputTypeValue: String) =
        authValidationUseCaseImpl.checkEmailAddressIsValid(inputTypeValue)

    fun checkPasswordIsValid(inputTypeValue: String) =
        authValidationUseCaseImpl.checkPasswordIsValid(inputTypeValue)
}