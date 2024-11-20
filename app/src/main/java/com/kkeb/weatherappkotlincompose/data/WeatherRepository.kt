package com.kkeb.weatherappkotlincompose.data

import com.kkeb.weatherappkotlincompose.utils.WEATHER_API_Key
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

//private val retrofit: Retrofit by lazy {
//    Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//}

private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

interface WeatherAPIService{

    @GET("weather?lat=23.85108534653475&lon=90.4115714057402&appid=${WEATHER_API_Key}&units=metric")
    suspend fun getCurrentWeather() : CurrentWeather

    @GET("forecast?lat=23.85108534653475&lon=90.4115714057402&appid=${WEATHER_API_Key}&units=metric")
    suspend fun getWeatherForecast() : ForecastWeather
}

object WeatherAPI{
    val retrofitService : WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}