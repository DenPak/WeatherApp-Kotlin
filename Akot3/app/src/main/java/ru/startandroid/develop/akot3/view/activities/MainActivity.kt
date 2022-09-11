package ru.startandroid.develop.akot3.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.startandroid.develop.akot3.adapters.FragmentAdapter
import ru.startandroid.develop.akot3.databinding.ActivityMainBinding
import ru.startandroid.develop.akot3.view.fragments.FragmentDays
import ru.startandroid.develop.akot3.view.fragments.FragmentHours
import ru.startandroid.develop.akot3.viewmodel.WeatherModel

import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val disposeBag = CompositeDisposable()
    private var listFragments:List<Fragment> = listOf(FragmentHours(),FragmentDays())
    private val list: List<String> = listOf("Hours", "Days")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getResViewPager()
        getResIntent()
        binding.imageView3.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getResIntent() {
        val intent: Intent = intent

        val cityName = intent.getStringExtra("city")
        val days = intent.getIntExtra("days", 1)
        getResponse(cityName.toString(), days.toString().toInt())
    }

    private fun getResponse(q: String, days: Int) {

       val weatherModel = ViewModelProvider(this).get(WeatherModel::class.java)
       val dispose = weatherModel.getResult(q, days)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val location = it.location
                val current = it.current
                binding.apply {
                    textDateAndTime.text = location.localtime
                    textCity.text = location.name
                    textTemperature.text = current.temp_c.toString()
                    textType.text = current.condition.text
                    Log.d("log", "icon - ${current.condition.icon}")
                    val icon = current.condition.icon
                    val stringBuilder = getParser(icon)
                    Log.d("log", "icon $stringBuilder")
                    Picasso.get().load(stringBuilder.toString()).into(imageIcon)
                }

            }, {
                Log.d("log", "error ${it.localizedMessage}")
            }, {

            })
        disposeBag.add(dispose)
    }

    private fun getParser(icon: String): StringBuilder {
        return StringBuilder(icon).insert(0, "https:")
    }


    private fun getResViewPager() {
        val tabLayoutMediator:TabLayoutMediator
        binding.apply {
           val fragmentAdapter = FragmentAdapter(this@MainActivity, listFragments)
            pager2.adapter = fragmentAdapter

            tabLayoutMediator = TabLayoutMediator(tabLayout, pager2) { tab, position ->
                tab.text = list[position]
            }
            tabLayoutMediator.attach()
        }
    }

    override fun onStop() {
        super.onStop()
       disposeBag.clear()
    }

    override fun onBackPressed() {

    }
}