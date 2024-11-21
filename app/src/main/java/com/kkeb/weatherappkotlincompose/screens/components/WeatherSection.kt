package com.kkeb.weatherappkotlincompose.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kkeb.weatherappkotlincompose.screens.Weather

@Composable
fun WeatherSection(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        CurrentWeatherSection(
            currentWeather = weather.currentWeather,
            modifier = modifier.weight(1f),
        )
        ForecastWeatherSection(
            forecastItemList = weather.forecastWeather.list!!
        )
    }
}