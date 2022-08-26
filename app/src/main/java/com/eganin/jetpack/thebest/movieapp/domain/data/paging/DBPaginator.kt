package com.eganin.jetpack.thebest.movieapp.domain.data.paging

interface DBPaginator<Item> {
    suspend fun loadItems()
}