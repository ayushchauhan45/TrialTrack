package com.example.trialtrack.auth_feature.domain.repository

import com.example.trialtrack.auth_feature.presentation.util.AuthResult

interface AuthRepository {

    suspend fun signUpUser(email:String,password:String,role:String): AuthResult<Unit>

    suspend fun loginUser(email:String,password:String): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>


}