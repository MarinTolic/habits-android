package com.example.habits_android.model

import kotlinx.serialization.Serializable

/**
 * The nutritional value of an edible substance, whether it is a type of food, meal or an ingredient.
 *
 * @param energy The amount of energy contained, expressed in Joules.
 * @param protein The amount of protein contained, expressed in grams.
 * @param carbohydrates The amount of protein contained, expressed in grams.
 * @param fat The amount of protein contained, expressed in grams.
 */
@Serializable
data class NutritionalValue(
    val energy: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fat: Int
) {
    /**
     * Multiplies the nutritional value.
     *
     * @param multiplier The amount of times the resulting nutritional value will be greater.
     *
     * @return The original nutritional value multiplied by [multiplier].
     */
    fun times(multiplier: Int): NutritionalValue = NutritionalValue(
        energy = this.energy * multiplier,
        protein = this.protein * multiplier,
        carbohydrates = this.carbohydrates * multiplier,
        fat = this.fat * multiplier,
    )
}
