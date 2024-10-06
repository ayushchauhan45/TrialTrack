package com.example.trialtrack.auth_feature.data.dto.request

data class SignUpRequest(
    val email:String,
    val password:String,
    val role:String
)
