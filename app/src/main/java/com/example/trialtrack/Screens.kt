package com.example.trialtrack

sealed class Screens(val route:String) {

    data object SignUpScreen : Screens("signup_screen")

    data object LoginScreen : Screens("login_screen")

    data object ClientDetailScreen : Screens("client_detail_screen")

    //NavGraphs

    data object AuthNavGraph : Screens("auth_nav_graph")

    data object UserNavGraph : Screens("user_nav_graph")

}