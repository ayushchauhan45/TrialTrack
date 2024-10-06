package com.example.trialtrack.auth_feature.presentation

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trialtrack.auth_feature.domain.AuthError
import com.example.trialtrack.auth_feature.domain.repository.AuthRepository
import com.example.trialtrack.auth_feature.presentation.util.AuthResult
import com.example.trialtrack.core.domain.states.StandardTextFieldStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
     private val authRepository: AuthRepository
) : ViewModel(){

     private val _emailState = mutableStateOf(StandardTextFieldStates())
     val emailState: State<StandardTextFieldStates> = _emailState

     private val _passwordState = mutableStateOf(SignUpPasswordState())
     val passwordState: State<SignUpPasswordState> = _passwordState

     private val _roleState = mutableStateOf(StandardTextFieldStates())
     val roleState: State<StandardTextFieldStates> = _roleState


     private val _signUpState = mutableStateOf(SignUpState())
     val signUpState: State<SignUpState> = _signUpState

     private val _authChannel = Channel<AuthResult<Unit>>()
     val authChannel =_authChannel.receiveAsFlow()



     fun onEvent(event: AuthEvent){
          when(event){
               is AuthEvent.ChangeEmail -> {
                    _emailState.value = _emailState.value.copy(
                         text = event.value
                    )
               }
               is AuthEvent.ChangePassword -> {
                    _passwordState.value = _passwordState.value.copy(
                         text = event.value
                    )
               }
               is AuthEvent.ChangeRole -> {
                    _roleState.value = _roleState.value.copy(
                         text = event.value
                    )
               }
               AuthEvent.LoginUser -> {

               }
               AuthEvent.SignUpUser -> {
                    validateEmail(_emailState.value.text)
                    validatePassword(_passwordState.value.text)
                    validateRole(_roleState.value.text)
                    signUp()
               }
               AuthEvent.TogglePasswordVisibility ->{
                    _passwordState.value = _passwordState.value.copy(
                         isPasswordVisible = !passwordState.value.isPasswordVisible
                    )
               }
          }
     }



     private fun validateEmail(email:String){
          if(email.isBlank()){
               _emailState.value = _emailState.value.copy(error= AuthError.EmptyTextFieldError)
               return
          }
          if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
               _emailState.value = _emailState.value.copy(error = AuthError.InValidEmail)
               return
          }
          _emailState.value = _emailState.value.copy(error = null)
     }

     private fun validateRole(role:String){
          if(role.isBlank()){
               _roleState.value = _roleState.value.copy(error = AuthError.EmptyTextFieldError)
               return
          }
          _roleState.value = _roleState.value.copy(error = null)
     }

     private fun validatePassword(password:String){
          if(password.isBlank()){
               _passwordState.value = _passwordState.value.copy(error = AuthError.EmptyTextFieldError)
               return
          }
          if(password.length < 8){
               _passwordState.value = _passwordState.value.copy(error = AuthError.PasswordTooShort)
               return
          }
          if (password.any { it in "@$!%*#?&"  }){
               _passwordState.value = _passwordState.value.copy(error = AuthError.PasswordDoesNotHaveSpecialChar)
               return
          }
          if (password.any { it.isUpperCase() }){
               _passwordState.value = _passwordState.value.copy(error = AuthError.PasswordDoesNotHaveUpperCase)
               return
          }

          _passwordState.value = _passwordState.value.copy(error = null)
     }

     private fun signUp(){
          if(emailState.value.error == null || passwordState.value.error == null || roleState.value.error == null){
               return
          }
          viewModelScope.launch {
               _signUpState.value = SignUpState(isLoading = true)
               val result = authRepository.signUpUser(
                    email = emailState.value.text,
                    password = passwordState.value.text,
                    role = roleState.value.text
               )
               _authChannel.send(result)
               _signUpState.value = SignUpState(isLoading = false)
          }
     }
}