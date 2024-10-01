package com.example.trialtrack.auth_feature.auth

interface AuthRepository {

    suspend fun signUpUser(email:String,password:String,role:String): AuthResult<Unit>

    suspend fun loginUser(email:String,password:String): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>


}