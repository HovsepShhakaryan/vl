package com.vylo.main.createfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vylo.common.BaseFragment
import com.vylo.main.R
import com.vylo.main.databinding.FragmentCreateBinding
import com.vylo.main.databinding.FragmentHomeBinding

class CreateFragment : BaseFragment<FragmentCreateBinding>() {

    override fun getViewBinding() = FragmentCreateBinding.inflate(layoutInflater)

    companion object {

        @JvmStatic
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinder = getViewBinding()
        return viewBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beginning()
    }

    private fun beginning() {

    }

}