package com.example.habits_android.networking

import com.example.habits_android.model.networking.MealRequest
import com.example.habits_android.model.networking.MealResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

/**
 * Allows the user to communicate with the server and in turn fetch and add new meals.
 */
class MealsService {

    /**
     * The Ktor Http client used for network communications.
     */
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            // This is a special Android proxy used for communicating with the local machine
            // the emulator is being run on. It contacts '0.0.0.0' on the host machine.
            host = "10.0.2.2"
            port = 8080
            url { protocol = URLProtocol.HTTP }
        }
    }

    /**
     * Fetches all the meals the user has entered.
     *
     * @return The list of meals if the request was successful, or a throwable if not.
     * Wrapped in a [Result] class.
     */
    suspend fun getAllMeals(): Result<List<MealResponse>> = runCatching {
        val response = client.get(urlString = "/meals/all")

        response.body<List<MealResponse>>()
    }

    /**
     * Adds a new meal the user has consumed.
     *
     * @param mealRequest The meal the user wants to add.
     *
     * @return The ID of the meal if the request was successful, or a throwable if not.
     * Wrapped in a [Result] class.
     */
    suspend fun addMeal(mealRequest: MealRequest): Result<Int> = runCatching {
        val response = client.post(urlString = "/meals") {
            contentType(ContentType.Application.Json)
            setBody(mealRequest)
        }

        response.body<Int>()
    }
}