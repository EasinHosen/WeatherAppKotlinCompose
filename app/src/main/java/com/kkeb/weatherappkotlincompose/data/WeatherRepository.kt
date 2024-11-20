package com.kkeb.weatherappkotlincompose.data

import com.kkeb.weatherappkotlincompose.network.WeatherAPI

interface WeatherRepository{
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getWeatherForecast(endUrl: String): ForecastWeather
}

class WeatherRepositoryImpl : WeatherRepository{
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return WeatherAPI.retrofitService.getCurrentWeather(endUrl)
    }

    override suspend fun getWeatherForecast(endUrl: String): ForecastWeather {
        return WeatherAPI.retrofitService.getWeatherForecast(endUrl)

    }
}