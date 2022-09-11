package ru.startandroid.develop.akot3.model.repository

import retrofit2.Call
import ru.startandroid.develop.akot3.data.Example

interface WeatherRepository {
    fun getCallExample(q:String,days:Int):Call<Example>
}