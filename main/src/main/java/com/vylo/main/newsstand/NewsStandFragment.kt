package com.vylo.main.newsstand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vylo.common.BaseFragment
import com.vylo.main.R
import com.vylo.main.databinding.FragmentHomeBinding
import com.vylo.main.databinding.FragmentNewsStandBinding

class NewsStandFragment : BaseFragment<FragmentNewsStandBinding>() {

    override fun getViewBinding() = FragmentNewsStandBinding.inflate(layoutInflater)

    companion object {

        @JvmStatic
        fun newInstance(): NewsStandFragment {
            return NewsStandFragment()
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