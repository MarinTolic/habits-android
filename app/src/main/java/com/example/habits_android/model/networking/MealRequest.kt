package com.example.habits_android.model.networking

import com.example.habits_android.model.NutritionalValue
import kotlinx.serialization.Serializable

/**
 * Represents a single meal the user has consumed to be used as a networking request.
 *
 * @param name The name of the meal.
 * @param weight The weight of the meal, expressed in grams.
 * @param averageNutritionalValue The nutritional value of the meal expressed as an average, per 100g.
 */
@Serializable
data class MealRequest(
    val name: String,
    val weight: Int,
    val averageNutritionalValue: NutritionalValue,
)