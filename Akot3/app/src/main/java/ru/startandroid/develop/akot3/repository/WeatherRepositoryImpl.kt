package ru.startandroid.develop.akot3.repository

import retrofit2.Call
import retrofit2.Retrofit
import ru.startandroid.develop.akot3.data.Example
import ru.startandroid.develop.akot3.repository.apiservice.ApiService

class WeatherRepositoryImpl(val retrofit: Retrofit,  val apiService:ApiService):WeatherRepository {


    companion object{
        const val KEY = "972d2f3885764b6e8e1115820223105 "
    }

    override fun getCallExample(q: String, days: Int): Call<Example> {
      return apiService.getJson(KEY,q,days,"no","no")
    }
}