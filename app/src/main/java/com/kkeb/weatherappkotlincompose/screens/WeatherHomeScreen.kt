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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kkeb.weatherappkotlincompose.customuis.AppBackground
import com.kkeb.weatherappkotlincompose.data.CurrentWeather
import com.kkeb.weatherappkotlincompose.data.ForecastWeather
import com.kkeb.weatherappkotlincompose.utils.degree
import com.kkeb.weatherappkotlincompose.utils.getFormattedDateTime
import com.kkeb.weatherappkotlincompose.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    onRefresh: () -> Unit,
    isConnected: Boolean,
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
                if (!isConnected) {
                    Text("No Internet Connection")
                } else {
                    when (uiState) {
                        is WeatherHomeUiState.Success -> {
                            WeatherSection(weather = uiState.weather)
                        }

                        is WeatherHomeUiState.Error -> {
                            ErrorSection(
                                errorMessage = uiState.message,
                                onRefresh = onRefresh
                            )
                        }

                        WeatherHomeUiState.Loading -> {
                            Text("Loading")
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ErrorSection(
    errorMessage: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            errorMessage,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = onRefresh
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = Color.White
            )
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
        ForecastWeatherSection(
            forecastItemList = weather.forecastWeather.list!!
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
            ) {}
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

@Composable
fun ForecastWeatherSection(
    forecastItemList: List<ForecastWeather.ForecastItem?>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(forecastItemList.size) { index ->
            ForecastItem(forecastItem = forecastItemList[index]!!)
        }
    }
}

@Composable
fun ForecastItem(
    forecastItem: ForecastWeather.ForecastItem,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                getFormattedDateTime(forecastItem.dt!!, "EEE"),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                getFormattedDateTime(forecastItem.dt, "hh:mm a"),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(forecastItem.weather?.first()?.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "${forecastItem.main?.temp} ${degree}C",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}