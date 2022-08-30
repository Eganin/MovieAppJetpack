package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.models

sealed class DetailsState {
    data class Loading(val id : Int) : DetailsState()
    object Error : DetailsState()
    object NoInfo : DetailsState()
    object Display : DetailsState()
}