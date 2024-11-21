package com.kkeb.weatherappkotlincompose.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kkeb.weatherappkotlincompose.data.models.ForecastWeather
import com.kkeb.weatherappkotlincompose.utils.degree
import com.kkeb.weatherappkotlincompose.utils.getFormattedDateTime
import com.kkeb.weatherappkotlincompose.utils.getIconUrl


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