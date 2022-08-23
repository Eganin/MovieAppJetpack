package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
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
    viewModel: MovieDetailsViewModel,
    movieInfo: MovieDetailsResponse,
    onCLick: (LocalTime) -> Unit
) {

    var localTime: LocalTime? = null
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState = dialogState,
        buttons = {
            positiveButton("OK") {
                /*
                меняем состояние переменных для скрытия date picker и time picker
                и спомощью lambda сохраняем время
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
                selectorColor = BackgroundColor,
                selectorTextColor = BackgroundColor,
                activeBackgroundColor = BackgroundColor,
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