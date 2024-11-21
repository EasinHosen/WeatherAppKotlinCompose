package com.kkeb.weatherappkotlincompose

import android.content.Context
import android.net.ConnectivityManager
import com.kkeb.weatherappkotlincompose.data.ConnectivityRepository
import com.kkeb.weatherappkotlincompose.data.DefaultConnectivityRepository
import com.kkeb.weatherappkotlincompose.data.WeatherRepository
import com.kkeb.weatherappkotlincompose.data.WeatherRepositoryImpl
import com.kkeb.weatherappkotlincompose.network.WeatherAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideWeatherAPIService(retrofit: Retrofit): WeatherAPIService {
        return retrofit.create(WeatherAPIService::class.java)
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java)
    }

    @Provides
    fun provideConnectivityRepository(connectivityManager: ConnectivityManager): ConnectivityRepository {
        return DefaultConnectivityRepository(connectivityManager)
    }

    @Provides
    fun provideWeatherRepository(weatherAPI: WeatherAPIService): WeatherRepository {
        return WeatherRepositoryImpl(weatherAPI)
    }

} 