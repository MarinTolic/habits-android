package com.example.habits_android.model

/**
 * The order of magnitude of a unit of measurement, e.g. kilo, mega, giga.
 *
 * @param prefix The prefix value for the order of magnitude, e.g. the 'k' in 'kCal'
 * @param orderOfMagnitude The actual numerical order of magnitude, e.g. 6 for 10^6.
 */
data class OrderOfMagnitude(
    val prefix: String,
    val orderOfMagnitude: Int
)