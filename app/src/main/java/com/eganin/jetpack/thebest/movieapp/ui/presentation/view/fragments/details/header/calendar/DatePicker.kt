package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun DatePicker(
    showTimePicker: MutableState<Boolean>,
    showDatePicker: MutableState<Boolean>,
    onCLick: (LocalDate) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var dateTime: LocalDate? = null
    MaterialDialog(dialogState = dialogState,
        buttons = {
            positiveButton("OK") {
                /*
                при рекопмоузе вызовем TimePicker в CalendarView
                и спомощью lambda сохраняем дату
                 */
                showTimePicker.value = !showTimePicker.value
                dateTime?.let {
                    onCLick(it)
                }
            }
            negativeButton("CANCEL") {
                //закрываем DatePicker
                showDatePicker.value = !showDatePicker.value
            }
        }) {
        datepicker(colors = DatePickerDefaults.colors(headerBackgroundColor = BackgroundColor)) { date ->
            dateTime = date
        }
    }

    dialogState.show()
}