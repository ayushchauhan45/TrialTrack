package com.example.trialtrack.auth_feature.presentation.util

sealed class AuthResult<T> (val data:T?=null){
    class Authorized<T>(data:T? = null): AuthResult<T>(data)
    class UnAuthorized<T>: AuthResult<T>()
    class UnknownError<T>: AuthResult<T>()
}