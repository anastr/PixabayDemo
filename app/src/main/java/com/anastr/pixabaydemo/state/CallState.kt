package com.anastr.pixabaydemo.state

sealed class CallState<out T> {
    object Loading: CallState<Nothing>()
    class Success<T>(val data: T): CallState<T>()
    class Error(val message: String): CallState<Nothing>()
}
