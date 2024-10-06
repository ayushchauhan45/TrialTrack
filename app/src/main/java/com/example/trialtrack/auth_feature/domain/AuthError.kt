package com.example.trialtrack.auth_feature.domain

import com.example.trialtrack.core.domain.states.Error

sealed class AuthError:Error(){
    data object EmptyTextFieldError:AuthError()
    data object InValidEmail:AuthError()
    data object PasswordTooShort:AuthError()
    data object PasswordDoesNotHaveSpecialChar:AuthError()
    data object PasswordDoesNotHaveUpperCase:AuthError()
}
