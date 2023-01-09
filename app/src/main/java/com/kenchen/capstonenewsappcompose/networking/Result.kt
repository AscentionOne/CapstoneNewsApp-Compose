package com.kenchen.capstonenewsappcompose.networking

/**
 * Generic class for remote result that holds value or exception
 * */

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
