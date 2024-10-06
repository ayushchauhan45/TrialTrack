package com.example.trialtrack.role_based_user_feature.domain.repository

import com.example.trialtrack.core.presentation.util.Resource
import com.example.trialtrack.core.presentation.util.SimpleResource

interface UserRepository {

    suspend fun clientDetail(
        name:String,
        age:Int,
        city:String,
        state:String,
        userId:String
    ):SimpleResource
}