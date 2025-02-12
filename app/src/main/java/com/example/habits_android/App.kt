package com.example.habits_android

import android.app.Application
import androidx.room.Room
import com.example.habits_android.database.MealDatabase
import com.example.habits_android.networking.MealsService
import com.example.habits_android.repository.MealRepository

class App: Application(){

     private val mealsDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MealDatabase::class.java, "meal-database"
        ).build()
    }

    private val mealsService by lazy {
        MealsService()
    }

    val mealRepository by lazy {
        MealRepository(mealsService, mealsDatabase)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun getInstance(): App {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }
}