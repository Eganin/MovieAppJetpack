package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.calendar

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.isPermanentlyDenied
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.AdultColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CalendarView(viewModel: MovieDetailsViewModel, movieInfo: MovieDetailsResponse) {

    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val date = remember { mutableStateOf(LocalDate.now()) }

    val permissionsState =
        rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.WRITE_CALENDAR))

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (showDatePicker.value) DatePicker(
        showDatePicker = showDatePicker,
        showTimePicker = showTimePicker,
    ) {
        date.value = it
    }
    if (showTimePicker.value) TimePicker(
        date = date.value,
        showTimePicker = showTimePicker,
        showDatePicker,
        viewModel = viewModel,
        movieInfo = movieInfo,
    )

    Image(
        painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24),
        contentDescription = "Calendar for scheduled viewing",
        modifier = Modifier
            .background(AdultColor)
            .padding(5.dp)
            .clickable {
                permissionsState.permissions.forEach { perm ->
                    when (perm.permission) {
                        Manifest.permission.WRITE_CALENDAR -> {
                            when {
                                perm.hasPermission -> {
                                    showDatePicker.value = !showDatePicker.value
                                }
                                perm.shouldShowRationale -> {

                                }
                                perm.isPermanentlyDenied() -> {

                                }
                            }
                        }
                    }
                }
            },
    )
}

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
                showTimePicker.value = !showTimePicker.value
                dateTime?.let {
                    onCLick(it)
                }
            }
            negativeButton("CANCEL") {
                showDatePicker.value = !showDatePicker.value
            }
        }) {
        datepicker { date ->
            dateTime = date
        }
    }

    dialogState.show()
}

@Composable
fun TimePicker(
    date: LocalDate,
    showTimePicker: MutableState<Boolean>,
    showDatePicker: MutableState<Boolean>,
    viewModel: MovieDetailsViewModel,
    movieInfo: MovieDetailsResponse,
) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState = dialogState,
        buttons = {
            positiveButton("OK") {
                showTimePicker.value = !showTimePicker.value
                showDatePicker.value = !showDatePicker.value
            }
            negativeButton("CANCEL") {
                showTimePicker.value = !showTimePicker.value
            }
        }) {
        timepicker { time ->
            date.month.value
            viewModel.writeDataCalendar(
                year = date.year,
                month = date.month.value,
                date = date.dayOfMonth,
                hourOfDay = time.hour,
                minute = time.minute,
                movie = movieInfo,
            )
        }
    }

    dialogState.show()
}