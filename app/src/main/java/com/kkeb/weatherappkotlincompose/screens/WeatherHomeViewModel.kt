package com.kkeb.weatherappkotlincompose.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkeb.weatherappkotlincompose.data.repositories.ConnectivityRepository
import com.kkeb.weatherappkotlincompose.data.models.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.models.ForecastWeather
import com.kkeb.weatherappkotlincompose.data.repositories.WeatherRepository
import com.kkeb.weatherappkotlincompose.utils.WEATHER_API_Key
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor (
    private val connectivityRepository: ConnectivityRepository,
    private val weatherRepository : WeatherRepository
) : ViewModel() {
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)

    val connectivityState: StateFlow<ConnectivityState> = connectivityRepository.connectivityState
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

        return weatherRepository.getCurrentWeather(endUrl)
    }

    private suspend fun getWeatherForecast(): ForecastWeather {
        val endUrl = "forecast?lat=$lat&lon=$lon&appid=${WEATHER_API_Key}&units=metric"

        return weatherRepository.getWeatherForecast(endUrl)
    }

    /*companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                val connectivityManager = application.getSystemService(ConnectivityManager::class.java)

                WeatherHomeViewModel(
                    connectivityRepository = DefaultConnectivityRepository(connectivityManager)
                )
            }
        }
    }*/
}