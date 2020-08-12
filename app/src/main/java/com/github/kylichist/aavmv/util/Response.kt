package com.github.kylichist.aavmv.util

sealed class Response
data class Success<T>(val response: T) : Response()
object Failure : Response()