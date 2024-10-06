package com.example.trialtrack.auth_feature.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.trialtrack.auth_feature.data.dto.request.LoginRequest
import com.example.trialtrack.auth_feature.data.dto.request.SignUpRequest
import com.example.trialtrack.auth_feature.data.remote.AuthApi
import com.example.trialtrack.auth_feature.domain.repository.AuthRepository
import com.example.trialtrack.auth_feature.presentation.util.AuthResult
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs:SharedPreferences
): AuthRepository {
    override suspend fun signUpUser(email:String,password:String,role:String): AuthResult<Unit> {
        return try{
             api.signUp(
                SignUpRequest(
                    email = email.trim(),
                    password = password.trim(),
                    role = role.trim(),
                )
            )
            loginUser(email,password)
        }catch (e:HttpException){
          if (e.code()== 401){
              AuthResult.UnAuthorized()
          }else{
              AuthResult.UnknownError()
          }
        }catch (e:Exception){
            Log.e("AuthRepository", "Unknown error during sign-up: ${e.message}", e)
            AuthResult.UnknownError()
        }
    }

    override suspend fun loginUser(email:String,password:String): AuthResult<Unit> {
        return try{
            val response = api.loginIn(
                LoginRequest(
                    email = email.trim(),
                    password = password.trim()
                )
            )
            prefs.edit().putString("jwt",response.token).apply()
            AuthResult.Authorized()
        }catch (e:HttpException){
            if (e.code()== 401){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e:Exception){
            Log.e("AuthRepository", "Unknown error during sign-up: ${e.message}", e)
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.UnAuthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        }catch (e:HttpException){
            if (e.code()== 401){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e:Exception){
            Log.e("AuthRepository", "Unknown error during sign-up: ${e.message}", e)
            AuthResult.UnknownError()
        }
    }
}