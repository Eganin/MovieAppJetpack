package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager

import androidx.work.Constraints
import androidx.work.WorkRequest

interface WorkerRepository {
    val constrains : Constraints
    val request : WorkRequest
}