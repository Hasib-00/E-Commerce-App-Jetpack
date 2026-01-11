package com.example.welife

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.welife.HomeScreen.HomeScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home" // Use lowercase for consistency
    ) {
        composable ("home"){
            HomeScreen(modifier,navController)
        }


    }
}