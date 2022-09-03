package com.duhan.feature_name.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.duhan.core.presentation.base.BaseViewModel
import com.duhan.feature_name.data.nameservice.Person
import com.duhan.feature_name.domain.GetNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NameListViewModel @Inject constructor(private val getNames: GetNames) : BaseViewModel() {
    private lateinit var _namesFlow: Flow<PagingData<Person>>
    val namesFlow: Flow<PagingData<Person>>
        get() = _namesFlow

    init {
        getAllNames()
    }

    private fun getAllNames() = launchPagingAsync({
        getNames.invoke().cachedIn(viewModelScope)
    }, {
        _namesFlow = it
    })
}