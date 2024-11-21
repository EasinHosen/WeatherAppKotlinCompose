package com.kkeb.weatherappkotlincompose.data

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import com.kkeb.weatherappkotlincompose.screens.ConnectivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ConnectivityRepository {
    val connectivityState: StateFlow<ConnectivityState>
}

class DefaultConnectivityRepository @Inject constructor(
    connectivityManager: ConnectivityManager
) : ConnectivityRepository {
    private val _connectivityState =
        MutableStateFlow<ConnectivityState>(ConnectivityState.Disconnected)

    private val callback = object : NetworkCallback() {
        override fun onAvailable(network: android.net.Network) {
            super.onAvailable(network)
            _connectivityState.value = ConnectivityState.Connected
        }

        override fun onLost(network: android.net.Network) {
            super.onLost(network)
            _connectivityState.value = ConnectivityState.Disconnected
        }
    }

    override val connectivityState: StateFlow<ConnectivityState> = _connectivityState

    init {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}