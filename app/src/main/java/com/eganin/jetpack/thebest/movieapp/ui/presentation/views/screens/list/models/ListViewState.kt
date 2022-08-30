package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.models

sealed class ListViewState {
    object Loading : ListViewState()
    object Error : ListViewState()
    object NoItems : ListViewState()
    object Display : ListViewState()
}