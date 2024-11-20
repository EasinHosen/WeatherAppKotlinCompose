package com.kkeb.weatherappkotlincompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kkeb.weatherappkotlincompose.customuis.AppBackground
import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.utils.degree
import com.kkeb.weatherappkotlincompose.utils.getFormattedDateTime
import com.kkeb.weatherappkotlincompose.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AppBackground(photoId = com.kkeb.weatherappkotlincompose.R.drawable.beautiful_skyscape_daytime_comp)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Weather App", style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.White,
                    )
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Surface(
                color = Color.Transparent,
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .wrapContentSize(),
            ) {
                when (uiState) {
                    is WeatherHomeUiState.Success -> {
                        WeatherSection(weather = uiState.weather)
                    }

                    is WeatherHomeUiState.Error -> {
                        Text(uiState.message)
                    }

                    WeatherHomeUiState.Loading -> {
                        Text("Loading")
                    }
                }
            }

        }
    }
}

@Composable
fun WeatherSection(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        CurrentWeatherSection(
            currentWeather = weather.currentWeather,
            modifier = modifier.weight(1f),
        )
    }
}

@Composable
fun CurrentWeatherSection(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            "${currentWeather.name}, ${currentWeather.sys?.country}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            getFormattedDateTime(currentWeather.dt!!),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "${currentWeather.main?.temp} ${degree}C",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Feels like ${currentWeather.main?.feelsLike} ${degree}C",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(currentWeather.weather?.first()?.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                "${currentWeather.weather.first()?.description}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    "Humidity ${currentWeather.main?.humidity?.toInt()} %",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Pressure ${currentWeather.main?.humidity?.toInt()} hPa",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Visibility ${currentWeather.main?.humidity?.toInt()} km",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Surface(
                color = Color.White,
                modifier = Modifier
                    .height(100.dp)
                    .width(2.dp)
            ){}
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    "Wind ${currentWeather.wind?.speed} m/s",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Sunrise ${getFormattedDateTime(currentWeather.sys?.sunrise!!, "hh:mm a")}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Sunset ${getFormattedDateTime(currentWeather.sys.sunset!!, "hh:mm a")}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}