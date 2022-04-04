package com.vylo.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vylo.common.util.ThrowStartScreen
import com.vylo.common.util.enums.ScreenType
import org.koin.android.ext.android.inject

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    protected lateinit var viewBinder: B
    private val baseViewModel: BaseViewModel by inject()

    companion object {

        @JvmStatic
        fun newInstance() = BaseFragment
    }

    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinder = getViewBinding()
        return viewBinder.root
    }

    protected fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected fun setScreenType(screenType: ScreenType) {
        baseViewModel.setScreenType(screenType)
    }

    protected fun throwStartScreen() {
        ThrowStartScreen(requireContext()).throwStartScreen()
    }
}