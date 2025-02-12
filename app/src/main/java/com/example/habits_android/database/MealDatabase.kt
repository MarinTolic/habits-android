package com.example.habits_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habits_android.database.model.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}