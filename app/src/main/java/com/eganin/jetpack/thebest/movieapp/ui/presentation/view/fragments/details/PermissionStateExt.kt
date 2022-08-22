package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied(): Boolean{
    return !shouldShowRationale && !hasPermission
}