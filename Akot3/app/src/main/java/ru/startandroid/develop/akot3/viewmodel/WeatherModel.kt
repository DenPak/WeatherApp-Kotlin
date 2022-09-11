package ru.startandroid.develop.akot3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import ru.startandroid.develop.akot3.data.Example
import ru.startandroid.develop.akot3.di.DaggerAppComponent

class WeatherModel : ViewModel() {
    private val weatherRepository = DaggerAppComponent.create().getWeatherRepositoryImpl()

    fun getResult(q: String, days: Int): Observable<Example> {
        return Observable.create() { emitter ->
            val call: Call<Example> = weatherRepository.getCallExample(q, days)
            call.enqueue(object : retrofit2.Callback<Example> {
                override fun onResponse(call: Call<Example>, response: Response<Example>) {
                    if (response.isSuccessful) {
                        response.body()?.let { emitter.onNext(it) }
                        Log.d("log", "response isSuccess")
                    } else {
                        Log.d("log", "fail ${call.request()}")
                    }
                }

                override fun onFailure(call: Call<Example>, t: Throwable) {
                    Log.d("log", "request -  ${call.request()}, error - ${t.localizedMessage}")
                }

            })
        }
    }


}