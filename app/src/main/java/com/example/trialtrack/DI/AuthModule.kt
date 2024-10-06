package com.example.trialtrack.DI

import android.content.SharedPreferences
import com.example.trialtrack.auth_feature.data.remote.AuthApi
import com.example.trialtrack.auth_feature.data.repository.AuthRepositoryImpl
import com.example.trialtrack.auth_feature.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthApi():AuthApi{
        return Retrofit.Builder().baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, sharedPreferences: SharedPreferences):AuthRepository{
        return AuthRepositoryImpl(authApi,sharedPreferences)
    }
}