package com.example.trialtrack.auth_feature.presentation

import com.example.trialtrack.core.domain.states.Error

data class SignUpPasswordState(
    val text:String = "",
    val error: Error? = null,
    val isPasswordVisible:Boolean = false
)
