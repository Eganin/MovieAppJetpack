package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimePicker(
    date: LocalDate,
    showTimePicker: MutableState<Boolean>,
    showDatePicker: MutableState<Boolean>,
    movieInfo: MovieDetailsResponse,
    onCLick: (LocalTime) -> Unit
) {

    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    var localTime: LocalTime? = null
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState = dialogState,
        buttons = {
            positiveButton("OK") {
                /*
                меняем состояние переменных для скрытия date picker и time picker
                и с помощью lambda сохраняем время
                 */
                showTimePicker.value = !showTimePicker.value
                showDatePicker.value = !showDatePicker.value
                localTime?.let { onCLick(it) }
            }
            negativeButton("CANCEL") {
                showTimePicker.value = !showTimePicker.value
            }
        }) {
        timepicker(
            colors = TimePickerDefaults.colors(
                selectorColor = JetMovieTheme.colors.secondaryBackground,
                selectorTextColor = JetMovieTheme.colors.secondaryBackground,
                activeBackgroundColor = JetMovieTheme.colors.secondaryBackground,
            )
        ) { time ->
            localTime = time
            //сохраняем данные во view model
            viewModel.writeDataCalendar(
                year = date.year,
                month = date.month.value - 1,
                date = date.dayOfMonth,
                hourOfDay = time.hour,
                minute = time.minute,
                movie = movieInfo,
            )
        }
    }

    dialogState.show()
}