package com.duhan.feature_name.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.duhan.feature_name.data.nameservice.DataSource
import com.duhan.feature_name.data.nameservice.FetchResponse
import com.duhan.feature_name.domain.Item
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

class NameServicePagingSource(private val nameService: DataSource) :
    PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int = 1

    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val pageNumber = params.key ?: 1
            val itemList = mutableListOf<Item>()
            val fetchResponse = nameService.fetchAsFlow(pageNumber).openSubscription().receive()
            fetchResponse.people.forEach {
                itemList.add(Item(it.fullName.trim(), it.id))
            }
            var nextPageNumber: Int? = null
            if (fetchResponse.next != null) {
                nextPageNumber = fetchResponse.next.toInt()
            }
            LoadResult.Page(
                data = itemList,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private suspend fun DataSource.fetchAsFlow(page: Int): BroadcastChannel<FetchResponse> {
        val broadcastChannel = BroadcastChannel<FetchResponse>(1)
        val strPage = if (page == 0) null else page.toString()
        fetch(strPage) { fetchResponse, fetchError ->
            if (fetchError != null) {
                broadcastChannel.cancel(CancellationException(fetchError.errorDescription))
            } else {
                if (fetchResponse != null) {
                    GlobalScope.launch {
                        broadcastChannel.send(fetchResponse)
                    }
                } else {
                    broadcastChannel.cancel(CancellationException("Can not retrieve data"))
                }
            }
        }
        return broadcastChannel
    }
}