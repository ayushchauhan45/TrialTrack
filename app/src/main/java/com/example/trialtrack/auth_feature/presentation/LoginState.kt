package com.example.trialtrack.auth_feature.presentation

data class LoginState(
    val email:String = "",
    val password:String = "",
    val isLoading:Boolean = false,
    val error : String? = ""
)
