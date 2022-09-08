package ru.startandroid.develop.akot3.adapters

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.startandroid.develop.akot3.R
import ru.startandroid.develop.akot3.data.Example
import ru.startandroid.develop.akot3.data.Hour
import ru.startandroid.develop.akot3.databinding.ListItemBinding
import java.util.*

class HoursAdapter : RecyclerView.Adapter<HoursAdapter.MyViewHolder>() {
    private var list: List<Example> = Collections.emptyList()
    private lateinit var hours: List<Hour>

    fun setHours(list: List<Example>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val example: Example = list[position]
        val forecast = example.forecast
        val forecastDay = forecast.forecastday
        for (i in forecastDay.indices) {
            hours = forecastDay[i].hour

        }
        val url = hours[position].condition.icon
        val stringBuilder = getParser(url)
        for (h in hours) {
            holder.textDate.text = hours[position].time
            holder.textTemp.text = "${hours[position].temp_c}°C"
            holder.textCondition.text = hours[position].condition.text
           Picasso.get().load(stringBuilder.toString()).into(holder.imageListItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ListItemBinding.bind(item)
        val textDate = binding.textDateList
        val textCondition = binding.textConditionList
        val textTemp = binding.textTempListItem
        val imageListItem = binding.imageIcon
    }

    private fun getParser(url: String): StringBuilder {
        return StringBuilder(url).insert(0, "https:")
    }
}