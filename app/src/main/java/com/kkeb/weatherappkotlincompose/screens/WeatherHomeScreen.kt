package com.kkeb.weatherappkotlincompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kkeb.weatherappkotlincompose.customuis.AppBackground
import com.kkeb.weatherappkotlincompose.screens.components.ErrorSection
import com.kkeb.weatherappkotlincompose.screens.components.WeatherSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    onRefresh: () -> Unit,
    isConnected: Boolean,
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AppBackground(photoId = com.kkeb.weatherappkotlincompose.R.drawable.beautiful_skyscape_daytime_comp)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Weather App", style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.White,
                    )
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Surface(
                color = Color.Transparent,
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .wrapContentSize(),
            ) {
                if (!isConnected) {
                    Text("No Internet Connection")
                } else {
                    when (uiState) {
                        is WeatherHomeUiState.Success -> {
                            WeatherSection(weather = uiState.weather)
                        }

                        is WeatherHomeUiState.Error -> {
                            ErrorSection(
                                errorMessage = uiState.message,
                                onRefresh = onRefresh
                            )
                        }

                        WeatherHomeUiState.Loading -> {
                            Text("Loading")
                        }
                    }
                }
            }

        }
    }
}
