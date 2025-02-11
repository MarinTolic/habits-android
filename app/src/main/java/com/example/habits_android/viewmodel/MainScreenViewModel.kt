package com.example.habits_android.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habits_android.model.NutritionalValue
import com.example.habits_android.model.networking.MealRequest
import com.example.habits_android.networking.MealsService
import com.example.habits_android.networking.mealsService
import com.example.habits_android.ui.screen.main.state.MealScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The ViewModel used for powering the main screen.
 */
class MainScreenViewModel(private val mealsService: MealsService) : ViewModel() {

    /**
     * The data to be shown to the user, in mutable form.
     */
    private var _data: MutableState<MealScreenState> = mutableStateOf(MealScreenState.LoadingState)

    /**
     * The data to be shown to the user.
     */
    val data by _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMeals()
        }
    }

    /**
     * Fetches meal and in case of success sets [data] to the loaded state, otherwise sets it
     * to the error state
     */
    private suspend fun fetchMeals() {
        mealsService.getAllMeals()
            .onFailure {
                _data.value = MealScreenState.ErrorState(throwable = it)
            }
            .onSuccess {
                _data.value = MealScreenState.LoadedState(meals = it)
            }
    }

    /**
     * Allows the user to add a new meal.
     *
     * @param name The name of the meal.
     * @param weight The weight of the meal.
     * @param energy The energy contained in the meal.
     * @param carbohydrates The carbohydrates contained in the meal.
     * @param protein The protein contained in the meal.
     * @param fat The fat contained in the meal.
     */
    fun addNewMeal(
        name: String,
        weight: Int,
        energy: Int,
        carbohydrates: Int,
        protein: Int,
        fat: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mealsService.addMeal(
                MealRequest(
                    name = name,
                    weight = weight,
                    averageNutritionalValue = NutritionalValue(
                        energy = energy / weight,
                        protein = protein / weight,
                        carbohydrates = carbohydrates / weight,
                        fat = fat / weight,
                    )
                )
            ).onSuccess {
                fetchMeals()
            }.onFailure {
                // TODO
            }
        }
    }

    companion object {
        /**
         * The ViewModel factory used to initialize the main screen ViewModel.
         */
        val factory = viewModelFactory {
            initializer {
                MainScreenViewModel(mealsService = mealsService)
            }
        }
    }
}
