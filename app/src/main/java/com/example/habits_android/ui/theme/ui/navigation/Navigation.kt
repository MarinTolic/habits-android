package com.example.habits_android.ui.theme.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habits_android.ui.theme.ui.screen.MainScreen
import com.example.habits_android.ui.theme.ui.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MainScreen) {

        composable<Route.MainScreen>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)

                )
            }
        ) {
            MainScreen(
                onSettingsClick = { navController.navigate(Route.SettingsScreen) }
            )
        }

        composable<Route.SettingsScreen>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
            }
        ) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed interface Route {
    @Serializable
    data object MainScreen : Route

    @Serializable
    data object SettingsScreen : Route
}