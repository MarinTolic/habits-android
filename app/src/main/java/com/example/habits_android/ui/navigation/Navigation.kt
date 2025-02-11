package com.example.habits_android.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habits_android.ui.screen.MainScreen
import com.example.habits_android.ui.screen.SettingsScreen
import kotlinx.serialization.Serializable

/**
 * A composable used for navigating between the various screens inside the app.
 */
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

/**
 * Represents a particular screen within the app.
 */
sealed interface Route {

    /**
     * Represents the main in-app screen.
     */
    @Serializable
    data object MainScreen : Route

    /**
     * Represents the in-app settings screen.
     */
    @Serializable
    data object SettingsScreen : Route
}