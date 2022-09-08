package ru.startandroid.develop.akot3.di

import dagger.Component
import ru.startandroid.develop.akot3.di.module.MyModule
import ru.startandroid.develop.akot3.repository.WeatherRepositoryImpl

@Component(modules = [MyModule::class])
interface AppComponent {

    fun getWeatherRepositoryImpl(): WeatherRepositoryImpl
}