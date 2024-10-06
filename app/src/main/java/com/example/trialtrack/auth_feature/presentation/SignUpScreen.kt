package com.example.trialtrack.auth_feature.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trialtrack.R
import com.example.trialtrack.auth_feature.domain.AuthError
import com.example.trialtrack.auth_feature.domain.AuthResult
import com.example.trialtrack.core.presentation.StandardTextField

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val emailState = authViewModel.emailState.value
    val passwordState = authViewModel.passwordState.value
    val roleState = authViewModel.roleState.value
    val signUpState = authViewModel.signUpState.value
    val authChannel = authViewModel.authChannel
    val context = LocalContext.current

    LaunchedEffect(authViewModel,context){
        authChannel.collect{result->
            when(result){
                is AuthResult.Authorized -> {
                    //Navigation
                }
                is AuthResult.UnAuthorized -> {
                    Toast.makeText(
                        context,
                        "You are not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An Unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(
            start = 24.dp,
            end = 24.dp,
            top = 24.dp,
            bottom = 24.dp
        ))
    {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            StandardTextField (
                text = emailState.text,
                hint = "E-mail",
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.ChangeEmail(it))
                },
                error = when(emailState.error){
                    is AuthError.EmptyTextFieldError -> {
                     stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InValidEmail ->{
                        stringResource(id = R.string.this_is_invalid_email)
                    }
                    else-> ""
                },
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(20.dp))
            StandardTextField (
                text = roleState.text,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.ChangeRole(it))
                },
                hint = "Role",
                error = when(roleState.error){
                    is AuthError.EmptyTextFieldError ->{
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    else -> ""
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            StandardTextField (
                text = passwordState.text,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.ChangePassword(it))
                },
                hint = "Password",
                error = when(passwordState.error){
                    is AuthError.EmptyTextFieldError ->{
                        stringResource(id = R.string.this_field_cant_be_empty)

                    }
                    is AuthError.PasswordTooShort ->{
                        stringResource(id = R.string.this_password_cant_be_this_short)

                    }
                    is AuthError.PasswordDoesNotHaveUpperCase ->{
                        stringResource(id = R.string.this_password_doesnt_have_uppercase_char)

                    }
                    is AuthError.PasswordDoesNotHaveSpecialChar->{
                        stringResource(id = R.string.this_password_does_not_have_special_char)
                    }
                    else -> ""
                },
                keyboardType = KeyboardType.Password,
                isPasswordVisible = passwordState.isPasswordVisible,
                onPasswordToggleClick = {
                    authViewModel.onEvent(AuthEvent.TogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                authViewModel.onEvent(AuthEvent.SignUpUser)
            },
            modifier = Modifier
               .align(Alignment.End))
            {
                Text(text = "Sign Up",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_an_account))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_in)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {

                }
        )
    }
}