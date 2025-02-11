package com.example.habits_android.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habits_android.R
import com.example.habits_android.model.networking.MealResponse

/**
 * Displays a list of meals the user has consumed.
 *
 * @param meals A list of meals the user has consumed
 * @param modifier used for styling.
 */
@Composable
fun MealList(
    meals: List<MealResponse>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(meals, key = { it.uuid }) {
            MealCard(
                modifier = Modifier.fillMaxWidth(),
                meal = it
            )
        }
    }
}

/**
 * A card displaying a single meal the user has consumed.
 *
 * @param meal The meal the user has consumed.
 * @param modifier used for styling.
 */
@Composable
fun MealCard(
    meal: MealResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(12.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = meal.name,
                fontSize = 20.sp
            )

            HorizontalDivider(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(1f)
                    .padding(vertical = 5.dp)
                    .width(2.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
            ) {
                val gramsSuffixString = stringResource(R.string.grams)

                Text(
                    text = "${stringResource(R.string.energy_units)}: ${meal.totalNutritionalValue.energy}${
                        stringResource(
                            R.string.joules
                        )
                    }"
                )

                Text(
                    text = "${stringResource(R.string.carbohydrates)}: ${meal.totalNutritionalValue.carbohydrates}$gramsSuffixString"
                )

                Text(
                    text = "${stringResource(R.string.fat)}: ${meal.totalNutritionalValue.fat}$gramsSuffixString"
                )

                Text(
                    text = "${stringResource(R.string.protein)}: ${meal.totalNutritionalValue.protein}$gramsSuffixString"
                )
            }
        }
    }
}