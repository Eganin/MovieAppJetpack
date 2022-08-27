package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header.calendar

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowAlertDialog(
    textMessage: String,
    actionType: AlertDialogActionType,
    permissionsState: MultiplePermissionsState,
    showAlertDialog: MutableState<Boolean>,
    launchSettings: MutableState<Boolean>,
) {
    /*
    изменяемая переменная для сокрытия и открытия AlertDialog при нажатии Button
     */
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
            showAlertDialog.value = !showAlertDialog.value
        }, title = {

            Text(
                text = "Permission",
                style = JetMovieTheme.typography.heading,
                color = JetMovieTheme.colors.primaryText,
            )
        },
            text = {
                Text(
                    text = textMessage, style = JetMovieTheme.typography.heading,
                    color = JetMovieTheme.colors.primaryText,
                )
            },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                    showAlertDialog.value = !showAlertDialog.value
                    when (actionType) {
                        AlertDialogActionType.START_SETTINGS -> {
                            //меняем состояние и при true в CalendarView запускаем activity settings
                            launchSettings.value = !launchSettings.value
                        }
                        AlertDialogActionType.REQUEST_PERMISSIONS -> {
                            //запрашиваем permissions
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                }) {

                    Text(
                        text = "OK", style = JetMovieTheme.typography.heading,
                        color = JetMovieTheme.colors.primaryText
                    )
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                    showAlertDialog.value = !showAlertDialog.value
                }) {
                    Text(
                        text = "CANCEL", style = JetMovieTheme.typography.heading,
                        color = JetMovieTheme.colors.primaryText
                    )
                }
            })
    }

}

// тип действия для диалога CalendarView
enum class AlertDialogActionType {
    START_SETTINGS,
    REQUEST_PERMISSIONS,
}