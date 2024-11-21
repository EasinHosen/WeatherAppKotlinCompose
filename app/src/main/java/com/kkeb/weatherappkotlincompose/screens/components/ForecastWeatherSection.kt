package com.kkeb.weatherappkotlincompose.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kkeb.weatherappkotlincompose.data.models.ForecastWeather


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
