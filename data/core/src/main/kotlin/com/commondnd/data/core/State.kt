package com.commondnd.data.core

sealed interface State<T> {

    class None<T> : State<T>
    class Loading<T> : State<T>
    data class Loaded<T>(
        val value: T
    ) : State<T>
    data class Error<T>(
        val error: Throwable
    ) : State<T>
}