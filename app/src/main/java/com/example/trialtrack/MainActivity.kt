package com.example.trialtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.trialtrack.auth_feature.presentation.SignUpScreen
import com.example.trialtrack.role_based_user_feature.presentation.ClientDetailScreen
import com.example.trialtrack.ui.theme.TrialTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrialTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screens.AuthNavGraph.route,
                    modifier = Modifier.padding(innerPadding))
                {
                    navigation(
                        route = Screens.AuthNavGraph.route,
                        startDestination = Screens.SignUpScreen.route,
                    ){
                        composable(Screens.SignUpScreen.route) {
                            SignUpScreen(navController = navController)
                        }
                        composable(Screens.LoginScreen.route) {

                        }
                    }
                    navigation(
                        route = Screens.UserNavGraph.route,
                        startDestination = Screens.ClientDetailScreen.route
                    ){
                        composable(Screens.ClientDetailScreen.route) {
                            ClientDetailScreen(navController = navController)
                        }
                    }

                }

                }
            }
        }
    }
}


