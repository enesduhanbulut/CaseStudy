package com.duhan.feature_name.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duhan.core.presentation.base.BaseFragment
import com.duhan.feature_name.R
import com.duhan.feature_name.databinding.FragmentNameListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NameListFragment : BaseFragment<FragmentNameListBinding, NameListViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_name_list
    private val viewModel: NameListViewModel by viewModels()

    override fun getVM(): NameListViewModel {
        return viewModel
    }

    override fun bindVM(binding: FragmentNameListBinding, vm: NameListViewModel) {
        with(binding) {
            lifecycleScope.launchWhenCreated {
                adapter = ListAdapter()
                viewModel.namesFlow.collectLatest {
                    adapter?.submitData(it)
                }
            }
        }
    }

}