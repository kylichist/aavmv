package com.github.kylichist.aavmv.util

sealed class Response
data class Successful<T>(val response: T) : Response()
object Fail : Response()