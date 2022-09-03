package com.duhan.feature_name.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.duhan.feature_name.data.nameservice.Person
import com.duhan.feature_name.data.paging.NameServicePagingSource
import kotlinx.coroutines.flow.Flow

class NameRepository(private val pagingSource: NameServicePagingSource) {
    fun getAllNames(): Flow<PagingData<Person>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { pagingSource }
    ).flow

}