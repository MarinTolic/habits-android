package com.example.habits_android.model

/**
* Used to dictate the energy units the app uses to display the data to the user.
 *
 * Note: The server always sends the energy units as Joules.
 *
 * @param name The name of the energy unit, e.g. Calorie, Joule, etc.
 * @param joulesConversionRatio How many units a single Joule is.
 *
 * Note: The conversion rate is always for two basic non-prefixed units, e.g. Joule to Calorie and
 * never for instance Joule to kiloCalorie.
 * @param orderOfMagnitude The order of magnitude of the energy unit. e.g. kilo, mega, giga, etc.
*/
data class EnergyUnit(
    val name: String,
    val joulesConversionRatio: Double,
    val orderOfMagnitude: OrderOfMagnitude,
)

/**
 * A list of all units the user can choose from.
 *
 * Note: Feel free to add more if you want, but don't make the list contain less than a single element
 * because [com.example.habits_android.persistence.datastore]'s default value depends on the
 * first element.
 */
val listOfUnits = listOf(
    EnergyUnit(
        name = "cal",
        joulesConversionRatio = 0.000000239006,
        orderOfMagnitude = OrderOfMagnitude(
            prefix = "k",
            orderOfMagnitude = 3
        )
    ),
    EnergyUnit(
        name = "Joule",
        joulesConversionRatio = 1.0,
        orderOfMagnitude = OrderOfMagnitude(
            prefix = "k",
            orderOfMagnitude = 3
        )
    )
)


