package com.example.habits_android.repository

import com.example.habits_android.database.MealDatabase
import com.example.habits_android.database.model.toMealEntity
import com.example.habits_android.database.model.toMealResponse
import com.example.habits_android.model.networking.MealRequest
import com.example.habits_android.model.networking.MealResponse
import com.example.habits_android.networking.MealsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Coordinates data between the server and the local database and transports it to the UI in a
 * seamless way.
 *
 * @param mealsService Used for networking operations.
 * @param mealDatabase Used for database operations.
 */
class MealRepository(
    private val mealsService: MealsService,
    private val mealDatabase: MealDatabase
) {

    /**
    * Fetches the data from the server and syncs the database with it.
    */
    suspend fun syncDatabase(): Result<Unit> = kotlin.runCatching {
        val response = mealsService.getAllMeals()

        if (response.isFailure) {
            return response.map { Unit }
        } else {
            val meals = response.getOrThrow()
                .map { it.toMealEntity() }

            mealDatabase.mealDao().insertAll(*meals.toTypedArray())
        }
    }

    /**
     * Fetches all the meals from the database.
     */
    fun getAllMeals(): Flow<List<MealResponse>> = mealDatabase.mealDao().getAll()
        .map { list ->
            list.map { it.toMealResponse() }
        }

    /**
     * Adds a new meal the user has consumed.
     *
     * @param mealRequest The meal the user wants to add.
     *
     * @return The ID of the meal if the request was successful, or a throwable if not.
     * Wrapped in a [Result] class.
     */
    suspend fun addMeal(mealRequest: MealRequest) = mealsService.addMeal(mealRequest)
}