package com.vylo.auth.activity

import android.os.Bundle
import com.vylo.auth.databinding.ActivityAuthBinding
import com.vylo.common.BaseActivity

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding() = ActivityAuthBinding.inflate(layoutInflater)

}