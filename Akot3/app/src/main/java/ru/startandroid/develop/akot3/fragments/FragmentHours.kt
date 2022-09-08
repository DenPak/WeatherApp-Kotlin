package ru.startandroid.develop.akot3.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.startandroid.develop.akot3.R
import ru.startandroid.develop.akot3.adapters.HoursAdapter
import ru.startandroid.develop.akot3.data.Example
import ru.startandroid.develop.akot3.data.Forecast
import ru.startandroid.develop.akot3.data.Forecastday
import ru.startandroid.develop.akot3.data.Hour
import ru.startandroid.develop.akot3.databinding.FragmentsHoursBinding
import ru.startandroid.develop.akot3.model.WeatherModel

class FragmentHours : Fragment() {

    private var mutableList: MutableList<Example> = mutableListOf()
    private val hoursAdapter: HoursAdapter = HoursAdapter()
    private val disposeBag =CompositeDisposable()

    private lateinit var hour: List<Hour>
    private lateinit var forecastDay: Forecastday
    private lateinit var binding: FragmentsHoursBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragments_hours, container, false);
        binding = FragmentsHoursBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherModel = ViewModelProvider(this)[WeatherModel::class.java]

        val intent = requireActivity().intent
        val city = intent.getStringExtra("city")
        val days = intent.getIntExtra("days", 0)
        binding.apply {
            recycleHours.layoutManager = LinearLayoutManager(requireContext())

            val disposable = city?.let {
                weatherModel.getResult(it, days)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val list =it.forecast.forecastday
                        for (i in list.indices) {
                            forecastDay = list[i]
                        }
                        hour = forecastDay.hour
                        for (h in hour.indices) {
                            mutableList.add(it)
                        }
                        hoursAdapter.setHours(mutableList)

                    }, {
                        Log.d("Log", "error ${it.localizedMessage}")
                    }, {

                    })
            }
            recycleHours.adapter = hoursAdapter

            disposable?.let { disposeBag.add(it) }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }

}