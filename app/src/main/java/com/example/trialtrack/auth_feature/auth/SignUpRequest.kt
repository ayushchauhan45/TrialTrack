package com.example.trialtrack.auth_feature.auth

data class SignUpRequest(
    val email:String,
    val password:String,
    val role:String
)
