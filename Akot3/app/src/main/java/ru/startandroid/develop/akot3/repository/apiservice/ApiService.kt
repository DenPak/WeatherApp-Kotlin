package ru.startandroid.develop.akot3.repository.apiservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.startandroid.develop.akot3.data.Example

interface ApiService {
    @GET("v1/forecast.json?")
    fun getJson(@Query("key")key:String,@Query("q")q:String,@Query("days") days:Int,@Query("aqi")aqi:String,@Query("alerts")alerts:String):Call<Example>
}