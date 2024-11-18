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
import androidx.compose.ui.tooling.preview.Preview
import com.kkeb.weatherappkotlincompose.customuis.AppBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(modifier: Modifier = Modifier) {
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
            ) { }

        }
    }
}

@Preview
@Composable
private fun WeatherHomeScreenPreview() {
    WeatherHomeScreen()
}