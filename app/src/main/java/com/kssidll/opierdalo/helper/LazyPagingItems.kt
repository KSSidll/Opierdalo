package com.kssidll.opierdalo.helper

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Checks whether the [LazyPagingItems] reported loaded state but is empty
 * @return true if [LazyPagingItems] reported loaded state but has no items, false otherwise
 */
fun <T> LazyPagingItems<T>.loadedEmpty(): Boolean where T: Any {
    return loadState.refresh is LoadState.NotLoading && itemCount == 0
}
