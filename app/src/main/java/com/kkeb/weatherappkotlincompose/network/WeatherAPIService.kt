package com.kkeb.weatherappkotlincompose.network


import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.ForecastWeather
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

    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String) : CurrentWeather

    @GET()
    suspend fun getWeatherForecast(@Url endUrl: String ) : ForecastWeather
}

object WeatherAPI{
    val retrofitService : WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}