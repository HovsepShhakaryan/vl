package com.vylo.artkai.presentation

import android.content.Intent
import android.os.Bundle
import com.vylo.artkai.databinding.ActivityStartBinding
import com.vylo.auth.activity.AuthActivity
import com.vylo.common.BaseActivity
import com.vylo.common.util.enums.ScreenType
import com.vylo.main.activity.MainFlawActivity

class StartActivity : BaseActivity<ActivityStartBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beginning()
    }

    private fun beginning() {
        when(screenType) {
            ScreenType.AUTH.type ->
                startActivity(Intent(this, AuthActivity::class.java))
            ScreenType.MAIN.type ->
                startActivity(Intent(this, MainFlawActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityStartBinding.inflate(layoutInflater)

}