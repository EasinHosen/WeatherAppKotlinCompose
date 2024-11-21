package com.kkeb.weatherappkotlincompose.network

import com.kkeb.weatherappkotlincompose.data.models.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.models.ForecastWeather
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherAPIService {

    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET()
    suspend fun getWeatherForecast(@Url endUrl: String): ForecastWeather
}