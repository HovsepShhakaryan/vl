package com.vylo.main.activity

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vylo.common.BaseActivity
import com.vylo.main.R
import com.vylo.main.databinding.ActivityMainFlawBinding

class MainFlawActivity : BaseActivity<ActivityMainFlawBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beginning()
    }

    private fun beginning() {
        val navController = findNavController(R.id.fragment)
        viewBinder.bottomNavigationBar.setupWithNavController(navController)
    }

    override fun getViewBinding() = ActivityMainFlawBinding.inflate(layoutInflater)


}