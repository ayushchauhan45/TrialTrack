package com.example.trialtrack.auth_feature.presentation

sealed class AuthEvent(){
    data class ChangeEmail(val value: String):AuthEvent()
    data class ChangePassword(val value: String):AuthEvent()
    data class ChangeRole(val value: String):AuthEvent()
    data object SignUpUser:AuthEvent()
    data object LoginUser:AuthEvent()
    data object TogglePasswordVisibility:AuthEvent()
}
