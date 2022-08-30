package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.models

import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.models.ListViewState

sealed class DetailsState {
    data class Loading(val id : Int) : DetailsState()
    object Error : DetailsState()
    object NoInfo : DetailsState()
    object Display : DetailsState()
}