package com.example.habits_android.ui.screen.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.habits_android.R
import com.example.habits_android.ui.screen.main.state.MealScreenState
import com.example.habits_android.ui.theme.HabitsandroidTheme
import com.example.habits_android.viewmodel.MainScreenViewModel


/**
 * The main screen of the app, used to display meals and add new ones.
 *
 * @param viewModel The ViewModel used for handling data operations.
 * @param onSettingsClick Listener called when the settings button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onSettingsClick: () -> Unit
) {
    HabitsandroidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.app_name))
                    },
                    actions = {
                        IconButton(onSettingsClick) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_settings_24),
                                contentDescription = null
                            )
                        }
                    })
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            var showDialog by remember { mutableStateOf(false) }

            Box {
                if (showDialog) {
                    AddMealDialog(
                        { showDialog = false },
                        onAddNewMeal = viewModel::addNewMeal
                    )
                }

                when (val data = viewModel.data) {
                    is MealScreenState.LoadingState -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )

                    is MealScreenState.LoadedState -> MealList(
                        meals = data.meals,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )

                    is MealScreenState.ErrorState -> Toast.makeText(
                        LocalContext.current,
                        "Uh oh",
                        Toast.LENGTH_LONG
                    ).show()
                }

                FloatingActionButton(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp),
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

/**
 * A dialog used for adding new meals.
 *
 * @param onDismissRequest Called when the user dismisses the dialog.
 * @param onAddNewMeal Called when the user adds a new meal.
 */
@Composable
fun AddMealDialog(
    onDismissRequest: () -> Unit,
    onAddNewMeal: (
        name: String,
        weight: Int,
        energy: Int,
        carbohydrates: Int,
        protein: Int,
        fat: Int
    ) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        var name by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }
        var energy by remember { mutableStateOf("") }
        var carbohydrates by remember { mutableStateOf("") }
        var fat by remember { mutableStateOf("") }
        var protein by remember { mutableStateOf("") }

        val outlineTextFieldTextStyle = TextStyle(fontSize = 12.sp)
        val labelFontSize = 8.sp

        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(4.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(top = 2.dp, start = 2.dp, end = 2.dp),
                value = name,
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.name),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = weight,
                onValueChange = { weight = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.weight),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = energy,
                onValueChange = { energy = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.energy),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = carbohydrates,
                onValueChange = { carbohydrates = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.carbohydrates),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = fat,
                onValueChange = { fat = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.fat),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = protein,
                onValueChange = { protein = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = outlineTextFieldTextStyle,
                label = {
                    Text(
                        fontSize = labelFontSize,
                        text = stringResource(R.string.protein),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {
                        onAddNewMeal(
                            name,
                            weight.toInt(),
                            energy.toInt(),
                            carbohydrates.toInt(),
                            fat.toInt(),
                            protein.toInt()
                        )
                        onDismissRequest()
                    }) {
                    Text(text = stringResource(R.string.add))
                }
            }
        }
    }
}