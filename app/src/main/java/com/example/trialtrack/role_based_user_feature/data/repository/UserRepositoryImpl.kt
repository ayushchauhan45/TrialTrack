package com.example.trialtrack.role_based_user_feature.data.repository

import com.example.trialtrack.core.presentation.util.Resource
import com.example.trialtrack.core.presentation.util.SimpleResource
import com.example.trialtrack.role_based_user_feature.data.dto.ClientDetailRequest
import com.example.trialtrack.role_based_user_feature.data.remote.UserApi
import com.example.trialtrack.role_based_user_feature.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
):UserRepository {

    override suspend fun clientDetail(
        name: String,
        age: Int,
        city: String,
        state: String,
        userId:String
    ): SimpleResource {
        val client = userApi.clientDetail(
            ClientDetailRequest(
                name = name,
                age = age,
                city = city,
                state = state
            ),
            userId = userId
        )
         return Resource.Successful(client)
    }
}