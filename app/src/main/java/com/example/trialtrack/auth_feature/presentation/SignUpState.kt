package com.example.trialtrack.auth_feature.presentation

data class SignUpState(
    val email:String = "",
    val password:String = "",
    val role:String = "",
    val isLoading:Boolean = false,
    val emailError:EmailError? = null,
    val passwordError:PasswordError? = null,
    val roleError: RoleError? =null
){
    sealed class EmailError(){
        data object EmptyEmail:EmailError()
    }
    sealed class PasswordError(){
        data object EmptyPassword:PasswordError()
        data object PasswordTooShort:PasswordError()
        data object PasswordDoesNotHaveSpecialChar:PasswordError()
        data object PasswordDoesNotHaveUpperCase:PasswordError()

    }
    sealed class RoleError(){
        data object EmptyRole:RoleError()
    }
}