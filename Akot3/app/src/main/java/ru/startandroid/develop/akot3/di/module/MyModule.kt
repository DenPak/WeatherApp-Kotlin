package ru.startandroid.develop.akot3.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.startandroid.develop.akot3.repository.WeatherRepositoryImpl
import ru.startandroid.develop.akot3.repository.apiservice.ApiService

@Module
class MyModule {
    companion object {
        const val BASE_URL = "http://api.weatherapi.com/ "
    }

    @Provides
    fun getWeatherRepositImpl(retrofit: Retrofit, apiService: ApiService): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(retrofit, apiService)
    }

    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}