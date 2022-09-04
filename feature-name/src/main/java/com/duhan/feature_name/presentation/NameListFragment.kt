package com.duhan.feature_name.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.duhan.core.presentation.base.BaseFragment
import com.duhan.core.presentation.base.PagingLoadStateAdapter
import com.duhan.feature_name.R
import com.duhan.feature_name.databinding.FragmentNameListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class NameListFragment : BaseFragment<FragmentNameListBinding, NameListViewModel>() {
    private val listAdapter = ListAdapter()
    override val layoutId: Int
        get() = R.layout.fragment_name_list
    private val viewModel: NameListViewModel by viewModels()

    override fun getVM(): NameListViewModel {
        return viewModel
    }

    override fun bindVM(binding: FragmentNameListBinding, vm: NameListViewModel) {
        with(binding) {
            items.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            items.addItemDecoration(
                RecyclerViewItemDecorator(
                    resources.getDimension(R.dimen.smallPadding).toInt()
                )
            )
            items.layoutManager = LinearLayoutManager(
                context
            )
            with(listAdapter) {
                swipeRefresh.setOnRefreshListener { refresh() }
                items.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )
                with(vm) {
                    lifecycleScope.launchWhenCreated {
                        namesFlow.collectLatest {
                            submitData(it)
                        }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                            if (it.refresh is LoadState.Error) {
                                showError("Try Again")
                            }
                        }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.distinctUntilChangedBy { it.refresh }
                            .filter { it.refresh is LoadState.NotLoading }
                            .collect { items.scrollToPosition(0) }
                    }
                }

            }
        }
    }

}