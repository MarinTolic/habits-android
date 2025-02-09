package com.example.habits_android.ui.theme.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.habits_android.R
import com.example.habits_android.ui.theme.HabitsandroidTheme


/**
 * The screen where the user can update app wide settings.
 *
 * @param onBackClick A listener triggered when the user presses the on back button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    HabitsandroidTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = stringResource(R.string.settings))
                }, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                })
            }, modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EnergyUnitSettings(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
            }
        }
    }
}

/**
 * Displays energy unit settings in a dropdown box.
 *
 * @param modifier Used for styling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyUnitSettings(modifier: Modifier = Modifier) {
    val options = listOf(
        stringResource(R.string.kilojoules), stringResource(R.string.kiloCalories)
    )
    var selectedOption by remember { mutableStateOf(options.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {

        TextField(
            label = {
                Text(
                    text = stringResource(R.string.energy_units)
                )
            },
            value = selectedOption,
            onValueChange = {
                selectedOption = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    selectedOption = it
                    isExpanded = false
                })
            }
        }
    }
}