package com.vylo.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import org.koin.android.ext.android.inject
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    protected lateinit var viewBinder: B
    abstract fun getViewBinding(): B
    private val baseViewModel: BaseViewModel by inject()
    val screenType get() = baseViewModel.getScreenType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = getViewBinding()
        setContentView(viewBinder.root)
    }

    fun replaceActivity(activity: AppCompatActivity) {
        startActivity(Intent(this, activity::class.java))
    }

    protected fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}