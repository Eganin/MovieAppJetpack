package com.eganin.jetpack.thebest.movieapp.base

interface EventHandler<T> {
    fun obtainEvent(event : T)
}