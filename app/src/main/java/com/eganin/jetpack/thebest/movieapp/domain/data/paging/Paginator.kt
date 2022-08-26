package com.eganin.jetpack.thebest.movieapp.domain.data.paging

import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies

interface Paginator <Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}