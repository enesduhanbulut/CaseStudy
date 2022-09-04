package com.duhan.feature_name.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.duhan.feature_name.data.nameservice.DataSource
import com.duhan.feature_name.data.paging.NameServicePagingSource
import com.duhan.feature_name.domain.Item
import kotlinx.coroutines.flow.Flow

class NameRepository(private val nameService: DataSource) {
    fun getAllNames(): Flow<PagingData<Item>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { NameServicePagingSource(nameService) }
    ).flow

}