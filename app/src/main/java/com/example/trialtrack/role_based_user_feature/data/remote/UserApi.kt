package com.example.trialtrack.role_based_user_feature.data.remote

import com.example.trialtrack.role_based_user_feature.data.dto.ClientDetailRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/client/detail")
    suspend fun clientDetail(
        @Body request: ClientDetailRequest
    )
}