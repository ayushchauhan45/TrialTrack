package com.example.trialtrack.core.domain.states

import com.example.trialtrack.auth_feature.presentation.SignUpState

data class StandardTextFieldStates(
    val text:String = "",
    val error: Error? = null
)
