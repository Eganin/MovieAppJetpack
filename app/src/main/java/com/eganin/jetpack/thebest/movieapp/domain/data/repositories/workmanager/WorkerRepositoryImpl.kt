package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import com.eganin.jetpack.thebest.movieapp.domain.data.workmanager.MyWorkerMovie
import java.util.concurrent.TimeUnit

class WorkerRepositoryImpl : WorkerRepository {
    /*
    Выполняем отложенную работу только когда
    устройство подключено к зарядке и wifi
     */
    override val constrains: Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build()

    override val request: WorkRequest = PeriodicWorkRequest.Builder(
        MyWorkerMovie::class.java,
        8,
        TimeUnit.HOURS,
        8,
        TimeUnit.HOURS,
    ).setConstraints(constrains)
        .build()
}