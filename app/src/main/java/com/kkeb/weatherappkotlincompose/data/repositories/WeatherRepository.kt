package com.kkeb.weatherappkotlincompose.data.repositories

import com.kkeb.weatherappkotlincompose.data.models.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.models.ForecastWeather
import com.kkeb.weatherappkotlincompose.network.WeatherAPIService
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getWeatherForecast(endUrl: String): ForecastWeather
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: WeatherAPIService
) : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return weatherAPI.getCurrentWeather(endUrl)
    }

    override suspend fun getWeatherForecast(endUrl: String): ForecastWeather {
        return weatherAPI.getWeatherForecast(endUrl)
    }
}