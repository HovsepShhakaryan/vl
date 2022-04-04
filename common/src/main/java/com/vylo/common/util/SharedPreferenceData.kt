package com.vylo.common.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.vylo.common.util.enums.ScreenType

class SharedPreferenceData(context: Context) {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_prefs_file",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    //_Key variables
    private val screenType: String = "screen_type"
    private val refreshToken: String = "refresh_token"
    private val token: String = "access_token"

    fun removeScreenType() {
        sharedPreferences
            .edit()
            .putInt(screenType, 1)
            .apply()
    }

    fun saveScreenType(type: ScreenType) {
        sharedPreferences
            .edit()
            .putInt(screenType, type.type)
            .apply()
    }

    val retrieveScreenType
        get() = sharedPreferences.getInt(screenType, ScreenType.AUTH.type)

    fun removeToken() {
        sharedPreferences
            .edit()
            .putString(token, "")
            .apply()
    }

    fun saveToken(accessToken: String) {
        sharedPreferences
            .edit()
            .putString(token, accessToken)
            .apply()
    }

    val retrieveToken
        get() = sharedPreferences.getString(token, null)

    fun removeRefreshToken() {
        sharedPreferences
            .edit()
            .putString(refreshToken, "")
            .apply()
    }

    fun saveRefreshToken(refToken: String) {
        sharedPreferences
            .edit()
            .putString(refreshToken, refToken)
            .apply()
    }

    val retrieveRefreshToken
        get() = sharedPreferences.getString(refreshToken, null)

}