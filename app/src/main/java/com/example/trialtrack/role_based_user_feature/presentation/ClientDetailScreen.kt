package com.example.trialtrack.role_based_user_feature.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ClientDetailScreen(
    navController: NavController
){
    Box(

        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 24.dp
            )
        ){
        Column {
            Text(text = "Hi!! You are welcome")
        }
    }
}