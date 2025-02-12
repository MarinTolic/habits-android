package com.example.habits_android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habits_android.model.NutritionalValue
import com.example.habits_android.model.networking.MealResponse
import java.util.UUID

/**
 * Represents a single meal the user has consumed with a flattened structure as to avoid
 * using type converters. See [referencing complex data](https://developer.android.com/training/data-storage/room/referencing-data)
 *
 * @param uuid the unique identifier of the meal.
 * @param name The name of the meal.
 * @param weight The weight of the meal, expressed in grams.
 * @param averageEnergy The amount of energy contained, expressed in Joules.
 * @param averageProtein The amount of protein contained, expressed in grams.
 * @param averageCarbohydrates The amount of protein contained, expressed in grams.
 * @param averageFat The amount of protein contained, expressed in grams.
 */
@Entity
data class MealEntity(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "averageEnergy") val averageEnergy: Int,
    @ColumnInfo(name = "averageProtein") val averageProtein: Int,
    @ColumnInfo(name = "averageCarbohydrates") val averageCarbohydrates: Int,
    @ColumnInfo(name = "averageFat") val averageFat: Int
)

fun MealResponse.toMealEntity() = MealEntity(
    uuid = this.uuid,
    name = this.name,
    weight = this.weight,
    averageEnergy = this.averageNutritionalValue.energy,
    averageProtein = this.averageNutritionalValue.protein,
    averageFat = this.averageNutritionalValue.fat,
    averageCarbohydrates = this.averageNutritionalValue.carbohydrates
)

fun MealEntity.toMealResponse() = MealResponse(
    uuid = this.uuid,
    name = this.name,
    weight = this.weight,
    averageNutritionalValue = NutritionalValue(
        energy = averageEnergy,
        protein = averageProtein,
        fat = averageFat,
        carbohydrates = averageCarbohydrates
    )
)