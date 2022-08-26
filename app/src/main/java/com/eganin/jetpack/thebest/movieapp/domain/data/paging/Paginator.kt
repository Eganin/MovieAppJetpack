package com.eganin.jetpack.thebest.movieapp.domain.data.paging

interface Paginator <Key, Item> {
    suspend fun loadNextItems(query : String="")
    fun reset()
}