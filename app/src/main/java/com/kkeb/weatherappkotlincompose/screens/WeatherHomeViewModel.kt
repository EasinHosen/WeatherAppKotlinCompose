package com.kkeb.weatherappkotlincompose.screens

import android.util.Log
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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherHomeViewModel: ViewModel() {
    private val weatherRepo: WeatherRepository = WeatherRepositoryImpl()
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)

    fun getWeatherData(){
        viewModelScope.launch{
           uiState = try{
               val currentWeather = async { getCurrentWeather() }.await()
               val forecastWeather = async { getWeatherForecast() }.await()

               WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))
           }catch (e: Exception){
               e.printStackTrace()
               WeatherHomeUiState.Error
           }
        }
    }

    private suspend fun getCurrentWeather(): CurrentWeather{
        val endUrl = "weather?lat=23.85108534653475&lon=90.4115714057402&appid=${WEATHER_API_Key}&units=metric"

        return weatherRepo.getCurrentWeather(endUrl)
    }

    private suspend fun getWeatherForecast(): ForecastWeather{
        val endUrl = "forecast?lat=23.85108534653475&lon=90.4115714057402&appid=${WEATHER_API_Key}&units=metric"

        return weatherRepo.getWeatherForecast(endUrl)
    }
}