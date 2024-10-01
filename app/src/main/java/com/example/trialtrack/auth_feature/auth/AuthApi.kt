package com.example.trialtrack.auth_feature.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("user/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest)


    @POST("user/login")
    suspend fun loginIn(
        @Body loginRequest: LoginRequest
    ):AuthRespond

    @GET("user/authenticate")
    suspend fun authenticate(
        @Header ("Authorization")token:String
    ):AuthResult<Unit>


}