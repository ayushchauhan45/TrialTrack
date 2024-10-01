package com.example.trialtrack.auth_feature.auth

import android.content.SharedPreferences
import android.util.Log
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs:SharedPreferences
):AuthRepository {
    override suspend fun signUpUser(email:String,password:String,role:String): AuthResult<Unit> {
        return try{
            val signUp =  api.signUp(
                SignUpRequest(
                    email = email,
                    password = password ,
                    role = role ,
                )
            )
             AuthResult.Authorized(signUp)
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
                    email = email,
                    password = password
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