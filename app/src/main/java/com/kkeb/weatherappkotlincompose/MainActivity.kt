package com.kkeb.weatherappkotlincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kkeb.weatherappkotlincompose.screens.WeatherHomeScreen
import com.kkeb.weatherappkotlincompose.screens.WeatherHomeViewModel
import com.kkeb.weatherappkotlincompose.ui.theme.WeatherAppKotlinComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val weatherHomeViewModel: WeatherHomeViewModel = viewModel()

    weatherHomeViewModel.getWeatherData()
    WeatherAppKotlinComposeTheme {
        WeatherHomeScreen(modifier = modifier)
    }
}