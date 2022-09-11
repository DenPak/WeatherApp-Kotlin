package ru.startandroid.develop.akot3.view.fragments


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
import ru.startandroid.develop.akot3.adapters.DaysAdapter
import ru.startandroid.develop.akot3.data.Example
import ru.startandroid.develop.akot3.databinding.FragmentsDaysBinding
import ru.startandroid.develop.akot3.viewmodel.WeatherModel

class FragmentDays : Fragment() {
    private lateinit var fragmentsDaysBinding: FragmentsDaysBinding
    private val daysAdapter = DaysAdapter()
    private var mutableList: MutableList<Example> = mutableListOf()
    private val disposeBag = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragments_days, container, false)
        fragmentsDaysBinding = FragmentsDaysBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherModel = ViewModelProvider(this)[WeatherModel::class.java]

        val intent = requireActivity().intent
        val city = intent.getStringExtra("city")
        val days = intent.getIntExtra("days", 3)

        fragmentsDaysBinding.apply {
            recycleDays.layoutManager = LinearLayoutManager(requireContext())

            val disposable = city?.let {
                weatherModel.getResult(it, days)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        val forecastDayList = it.forecast.forecastday

                        for (i in forecastDayList.indices) {
                            mutableList.add(it)
                        }
                        daysAdapter.setData(mutableList)

                    }, {
                        Log.d("log", "error ${it.localizedMessage}")
                    })
            }
            recycleDays.adapter = daysAdapter
            disposable?.let { disposeBag.add(it) }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }


}