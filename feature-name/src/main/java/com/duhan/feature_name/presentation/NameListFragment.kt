package com.duhan.feature_name.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.duhan.feature_name.R

class NameListFragment : Fragment() {

    companion object {
        fun newInstance() = NameListFragment()
    }

    private lateinit var viewModel: NameListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_name_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NameListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}