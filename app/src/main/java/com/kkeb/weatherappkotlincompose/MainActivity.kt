package com.kkeb.weatherappkotlincompose

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kkeb.weatherappkotlincompose.screens.ConnectivityState
import com.kkeb.weatherappkotlincompose.screens.WeatherHomeScreen
import com.kkeb.weatherappkotlincompose.screens.WeatherHomeUiState
import com.kkeb.weatherappkotlincompose.screens.WeatherHomeViewModel
import com.kkeb.weatherappkotlincompose.ui.theme.WeatherAppKotlinComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client: FusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            WeatherApp(client)
        }
    }
}

@Composable
fun WeatherApp(
    client: FusedLocationProviderClient,
    modifier: Modifier = Modifier
) {
    val weatherHomeViewModel: WeatherHomeViewModel =
        viewModel(factory = WeatherHomeViewModel.Factory)
    val context = LocalContext.current
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        locationPermissionGranted = granted
    }
    LaunchedEffect(Unit) {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!isPermissionGranted) {
            launcher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            locationPermissionGranted = true
        }
    }
    LaunchedEffect(locationPermissionGranted) {
        if (locationPermissionGranted) {
            client.lastLocation.addOnSuccessListener { location ->
                weatherHomeViewModel.setLocation(location.latitude, location.longitude)
                weatherHomeViewModel.getWeatherData()
            }
        }
    }
    val connectivityState by weatherHomeViewModel.connectivityState.collectAsState()
    WeatherAppKotlinComposeTheme {
        WeatherHomeScreen(
            onRefresh = {
                weatherHomeViewModel.uiState = WeatherHomeUiState.Loading
                weatherHomeViewModel.getWeatherData()
            },
            isConnected = connectivityState is ConnectivityState.Connected,
            uiState = weatherHomeViewModel.uiState,
            modifier = modifier
        )
    }
}