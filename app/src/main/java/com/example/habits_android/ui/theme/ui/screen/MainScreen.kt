package com.example.habits_android.ui.theme.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.habits_android.R
import com.example.habits_android.ui.theme.HabitsandroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
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
            Text(
                text = "MainScreen",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}