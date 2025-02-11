package com.example.habits_android.ui.screen.main.state

import com.example.habits_android.model.networking.MealResponse

/**
 * The different possible states of the meal screen.
 */
sealed interface MealScreenState {

    /**
     * The loading state, meaning the meals have not been fetched.
     */
    data object LoadingState : MealScreenState

    /**
     * The loaded state, meaning meals have been successfully fetched.
     *
     * @param meals A list of meals to be shown to the user.
     */
    data class LoadedState(val meals: List<MealResponse>) : MealScreenState

    /**
     * There has been an error fetching the meals.
     *
     * @param throwable The cause of the error.
     */
    data class ErrorState(val throwable: Throwable) : MealScreenState
}