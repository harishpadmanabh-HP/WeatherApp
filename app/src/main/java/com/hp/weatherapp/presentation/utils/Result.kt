package com.hp.weatherapp.presentation.utils

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}


sealed class NetworkResult<out T> {
    data class Success<out R>(val value: R) : NetworkResult<R>()
    object Loading : NetworkResult<Nothing>()
    data class Failure(
        val uiText: String?
    ) : NetworkResult<Nothing>()
}


inline fun <reified T> NetworkResult<T>.doIfFailure(callback: (error: String?) -> Unit) {
    if (this is NetworkResult.Failure) {
        callback(uiText)
    }
}

inline fun <reified T> NetworkResult<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is NetworkResult.Success) {
        callback(value)
    }
}

inline fun <reified T> NetworkResult<T>.doIfLoading(callback: () -> Unit) {
    if (this is NetworkResult.Loading) {
        callback()
    }
}