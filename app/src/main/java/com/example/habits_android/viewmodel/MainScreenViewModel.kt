package com.example.habits_android.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habits_android.App
import com.example.habits_android.model.NutritionalValue
import com.example.habits_android.model.networking.MealRequest
import com.example.habits_android.repository.MealRepository
import com.example.habits_android.ui.screen.main.state.MealScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

/**
 * The ViewModel used for powering the main screen.
 *
 * @param mealsRepository The repository used for fetching and storing meal data.
 */
class MainScreenViewModel(private val mealsRepository: MealRepository) : ViewModel() {

    /**
     * The data to be shown to the user, in mutable form.
     */
    private var _data: MutableState<MealScreenState> = mutableStateOf(MealScreenState.LoadingState)

    /**
     * The data to be shown to the user.
     */
    val data by _data

    init {
        fetchMeals()
        viewModelScope.launch(Dispatchers.IO) {
            mealsRepository.syncDatabase()
        }
    }

    /**
     * Fetches meal and in case of success sets [data] to the loaded state, otherwise sets it
     * to the error state
     */
    private fun fetchMeals() {
        mealsRepository.getAllMeals()
            .onEach {
                _data.value = MealScreenState.LoadedState(meals = it)
            }
            .catch {
                _data.value = MealScreenState.ErrorState(throwable = it)
            }
            .launchIn(viewModelScope.plus(Dispatchers.IO))
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
            mealsRepository.addMeal(
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
                mealsRepository.syncDatabase()
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
                MainScreenViewModel(mealsRepository = App.getInstance().mealRepository)
            }
        }
    }
}
