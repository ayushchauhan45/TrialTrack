package com.example.trialtrack.auth_feature.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trialtrack.auth_feature.auth.AuthRepository
import com.example.trialtrack.auth_feature.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
     private val authRepository: AuthRepository
) : ViewModel(){

     private val _state = mutableStateOf(SignUpState())
     val state: State<SignUpState> = _state

     private val _authChannel = Channel<AuthResult<Unit>>()
     val authChannel =_authChannel.receiveAsFlow()



     fun validateEmail(email:String){
          if(email.isBlank()){
               _state.value = _state.value.copy(emailError = SignUpState.EmailError.EmptyEmail)
               return
          }
          _state.value = _state.value.copy(emailError = null)
     }

     fun validateRole(role:String){
          if(role.isBlank()){
               _state.value = _state.value.copy(roleError = SignUpState.RoleError.EmptyRole)
               return
          }
          _state.value = _state.value.copy(roleError = null)
     }
     fun validatePassword(password:String){
          if(password.isBlank()){
               _state.value = state.value.copy(passwordError = SignUpState.PasswordError.EmptyPassword)
               return
          }
          if(password.length < 8){
               _state.value = state.value.copy(passwordError = SignUpState.PasswordError.PasswordTooShort)
               return
          }
          if (password.any { it in "@$!%*#?&"  }){
               _state.value = state.value.copy(passwordError = SignUpState.PasswordError.PasswordDoesNotHaveSpecialChar)
               return
          }
          if (password.any { it.isUpperCase() }){
               _state.value = state.value.copy(passwordError = SignUpState.PasswordError.PasswordDoesNotHaveUpperCase)
               return
          }

          _state.value = _state.value.copy(passwordError = null)
     }

     fun singUp(){
          viewModelScope.launch {
               val result = authRepository.signUpUser(
                    email = validateEmail(_state.value.email).toString(),
                    password = validatePassword(_state.value.password).toString(),
                    role = validateRole(_state.value.role).toString()
               )
               when(_state.value.passwordError){
                    SignUpState.PasswordError.EmptyPassword -> TODO()
                    SignUpState.PasswordError.PasswordDoesNotHaveSpecialChar -> TODO()
                    SignUpState.PasswordError.PasswordDoesNotHaveUpperCase -> TODO()
                    SignUpState.PasswordError.PasswordTooShort -> TODO()
                    null -> TODO()
               }
          }
     }





}