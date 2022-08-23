package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.calendar

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.isPermanentlyDenied
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.utils.ShowSnackBar
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.AdultColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CalendarView(
    viewModel: MovieDetailsViewModel,
    movieInfo: MovieDetailsResponse,
    scaffoldState: ScaffoldState
) {

    /*
    showDatePicker,showTimePicker - переменные для показа dialog picker
     */
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    /*
    date,time - переменные для сохранения даты и времени
     */
    val date = remember { mutableStateOf(LocalDate.now()) }
    val time = remember { mutableStateOf(LocalTime.now()) }
    /*
    showSnackBarDate,showSnackBarTime-показ SnackBar из picker dialog
     */
    val showSnackBarDate = remember { mutableStateOf(false) }
    val showSnackBarTime = remember { mutableStateOf(false) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val textMessageAlertDialog = remember { mutableStateOf("") }
    /*
    launchSettings-изменяемая переменная для старта активити настроек из @Composable ShowAlertDialog
    actionTypeAlertDialog-для вызова действия из AlertDialog
     */
    val launchSettings = remember { mutableStateOf(false) }
    val actionTypeAlertDialog =
        remember { mutableStateOf(AlertDialogActionType.REQUEST_PERMISSIONS) }

    val permissionsState =
        rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.WRITE_CALENDAR))

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            //запрашиваем permission, если активити в ON START
            if (event == Lifecycle.Event.ON_START) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    /*
    если есть permission запускаем DatePicker,
    а также сохраняем дату и изменяем переменную для показа SnackBar
     */
    if (showDatePicker.value) DatePicker(
        showDatePicker = showDatePicker,
        showTimePicker = showTimePicker,
    ) {
        date.value = it
        showSnackBarDate.value = !showSnackBarDate.value
    }
    /*
    после DatePicker запускаем TimePicker
    сохраняем время и изменяем переменную для показа SnackBar
     */
    if (showTimePicker.value) TimePicker(
        date = date.value,
        showTimePicker = showTimePicker,
        showDatePicker,
        viewModel = viewModel,
        movieInfo = movieInfo,
    ) {
        time.value = it
        showSnackBarTime.value = !showSnackBarTime.value
    }
    if (showSnackBarDate.value) {
        with(date.value) {
            ShowSnackBar(
                text = "you choosed ${year}/${month.value}/${dayOfMonth}",
                scaffoldState = scaffoldState
            )
        }
    }
    if (showSnackBarTime.value) {
        with(time.value) {
            ShowSnackBar(text = "you choosed ${hour}/${minute}", scaffoldState = scaffoldState)
        }
    }
    /*
    запуск AlertDialog в зависимости от permission
     */
    if (showAlertDialog.value) {
        ShowAlertDialog(
            textMessage = textMessageAlertDialog.value,
            permissionsState = permissionsState,
            actionType = actionTypeAlertDialog.value,
            showAlertDialog = showAlertDialog,
            launchSettings = launchSettings,
        )
    }

    /*
    запускаем activity settings из AlertDialog
     */
    if (launchSettings.value) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + LocalContext.current.packageName)
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        LocalContext.current.applicationContext.startActivity(intent)
        launchSettings.value = !launchSettings.value
    }
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
                                    //при рекопмоузе показываем DatePicker
                                    showDatePicker.value = !showDatePicker.value
                                }
                                perm.shouldShowRationale -> {
                                    /*
                                    при рекомпоузе показваем AlertDialog
                                    и сохраняем туда текст и тип действия для запроса permission
                                     */
                                    showAlertDialog.value = !showAlertDialog.value
                                    textMessageAlertDialog.value =
                                        "To continue please grant access to calendar"
                                    actionTypeAlertDialog.value =
                                        AlertDialogActionType.REQUEST_PERMISSIONS
                                }
                                perm.isPermanentlyDenied() -> {
                                    /*
                                    при рекомпоузе показваем AlertDialog
                                    и сохраняем туда текст и тип действия для запкска
                                    activity settings
                                     */
                                    showAlertDialog.value = !showAlertDialog.value
                                    textMessageAlertDialog.value =
                                        "his feature is not available without calendar permissions. Open app settings?"
                                    actionTypeAlertDialog.value =
                                        AlertDialogActionType.START_SETTINGS
                                }
                            }
                        }
                    }
                }
            },
    )
}
