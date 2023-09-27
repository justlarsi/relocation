package com.example.relocationapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.relocationapp.data.AuthviewModel
import com.example.relocationapp.data.FormViewModel
import com.example.relocationapp.ui.theme.screens.aboutus.AboutUsScreen
import com.example.relocationapp.ui.theme.screens.details.Detailsscreen
import com.example.relocationapp.ui.theme.screens.home.MoveDetailsScreen
import com.example.relocationapp.ui.theme.screens.login.LoginScreen
import com.example.relocationapp.ui.theme.screens.splash.SplashScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String= ROUTE_SPLASH,
    authviewModel: AuthviewModel,
    formViewModel: FormViewModel,
)
{

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ROUTE_SPLASH
    ) {
        composable(ROUTE_HOME) {
            MoveDetailsScreen(navController,formViewModel,authviewModel)
        }
        composable(ROUTE_SPLASH){
            SplashScreen(navController)
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(navController,authviewModel)
        }
        composable(ROUTE_DETAILS){
            Detailsscreen(navController)
        }
        composable(ROUTE_ABOUTUS){
            AboutUsScreen(navController)
    }
}
}