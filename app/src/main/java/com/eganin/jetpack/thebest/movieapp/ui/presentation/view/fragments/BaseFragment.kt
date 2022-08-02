package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun setState(state: MoviesListViewModel.State, progressBar: ProgressBar) {
        when (state) {
            MoviesListViewModel.State.Default -> setLoading(
                loading = true,
                progressBar = progressBar
            )
            MoviesListViewModel.State.Error -> {
                showSnackBar(
                    textMessage = getString(R.string.error_data_loading_snckbar_message)
                )
                setLoading(loading = false, progressBar = progressBar)
            }
            MoviesListViewModel.State.Loading -> setLoading(
                loading = true,
                progressBar = progressBar
            )
            MoviesListViewModel.State.Success -> setLoading(
                loading = false,
                progressBar = progressBar
            )
        }
    }

    private fun showSnackBar(textMessage: String) {
        view?.let { Snackbar.make(it, textMessage, Snackbar.LENGTH_LONG) }
    }

    private fun setLoading(loading: Boolean, progressBar: ProgressBar) {
        progressBar.isVisible = loading
    }
}