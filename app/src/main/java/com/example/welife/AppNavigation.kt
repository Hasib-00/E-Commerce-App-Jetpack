package com.example.welife

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home" // Use lowercase for consistency
    ) {
       // composable ("home"){
       //   HomeScreen(modifier,navController)
       // }


    }
}