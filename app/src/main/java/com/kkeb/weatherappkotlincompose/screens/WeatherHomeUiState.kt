package com.kkeb.weatherappkotlincompose.screens

import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.ForecastWeather

data class Weather(
    val currentWeather: CurrentWeather,
    val forecastWeather: ForecastWeather
)

sealed interface WeatherHomeUiState{
    data class Success(val weather: Weather): WeatherHomeUiState
    data class Error(val message: String): WeatherHomeUiState
    data object Loading: WeatherHomeUiState
}