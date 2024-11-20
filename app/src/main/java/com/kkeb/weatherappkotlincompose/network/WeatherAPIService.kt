package com.kkeb.weatherappkotlincompose.network


import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.ForecastWeather
import com.kkeb.weatherappkotlincompose.utils.WEATHER_API_Key
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

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
    suspend fun getCurrentWeather(@Url endUrl: String) : CurrentWeather

    @GET("forecast?lat=23.85108534653475&lon=90.4115714057402&appid=${WEATHER_API_Key}&units=metric")
    suspend fun getWeatherForecast(@Url endUrl: String ) : ForecastWeather
}

object WeatherAPI{
    val retrofitService : WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}