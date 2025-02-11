package com.example.habits_android.model.networking

import com.example.habits_android.model.NutritionalValue
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a single meal the user has consumed in the form of a server response.
 *
 * @param uuid the unique identifier of the meal.
 * @param name The name of the meal.
 * @param weight The weight of the meal, expressed in grams.
 * @param averageNutritionalValue The nutritional value of the meal expressed as an average, per 100g.
 */
@Serializable
data class MealResponse(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    val weight: Int,
    val averageNutritionalValue: NutritionalValue,
) {

    /**
     * The total nutritional value of the meal.
     */
    val totalNutritionalValue: NutritionalValue = averageNutritionalValue.times(weight)
}