package com.kkeb.weatherappkotlincompose.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.ForecastWeather
import com.kkeb.weatherappkotlincompose.data.WeatherRepository
import com.kkeb.weatherappkotlincompose.data.WeatherRepositoryImpl
import com.kkeb.weatherappkotlincompose.utils.WEATHER_API_Key
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherHomeViewModel : ViewModel() {
    private val weatherRepo: WeatherRepository = WeatherRepositoryImpl()
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)
    private var lat = 0.0
    private var lon = 0.0

    fun setLocation(latitude: Double, longitude: Double) {
        lat = latitude
        lon = longitude
    }

    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        uiState = WeatherHomeUiState.Error(e.message.toString())
    }

    fun getWeatherData() {
        viewModelScope.launch(exceptionHandler) {
            uiState = try {
                val currentWeather = async { getCurrentWeather() }.await()
                val forecastWeather = async { getWeatherForecast() }.await()

                WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))
            } catch (e: Exception) {
                e.printStackTrace()
                WeatherHomeUiState.Error(e.message.toString())
            }
        }
    }

    private suspend fun getCurrentWeather(): CurrentWeather {
        val endUrl = "weather?lat=$lat&lon=$lon&appid=${WEATHER_API_Key}&units=metric"

        return weatherRepo.getCurrentWeather(endUrl)
    }

    private suspend fun getWeatherForecast(): ForecastWeather {
        val endUrl = "forecast?lat=$lat&lon=$lon&appid=${WEATHER_API_Key}&units=metric"

        return weatherRepo.getWeatherForecast(endUrl)
    }
}