package com.example.trialtrack.core.presentation.util


typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(data:T? = null, message:String?=null) {
     class Successful<T>(data: T?):Resource<T>(data)
    class Error<T>(data: T? = null,message: String?) : Resource<T>(data,message)
}