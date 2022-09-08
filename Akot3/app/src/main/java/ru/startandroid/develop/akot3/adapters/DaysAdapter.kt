package ru.startandroid.develop.akot3.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.startandroid.develop.akot3.R
import ru.startandroid.develop.akot3.data.Example

import ru.startandroid.develop.akot3.data.Forecastday

import ru.startandroid.develop.akot3.databinding.ListItemBinding
import java.util.*

class DaysAdapter : RecyclerView.Adapter<DaysAdapter.MyViewHolder>() {
    private var list: List<Example> = Collections.emptyList()

    fun setData(list: List<Example>) {
        this.list = list;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val example: Example = list[position]
        val forecastDay = example.forecast.forecastday
        val day = forecastDay[position].day.condition.icon

        val stringBuilder = getParser(day)

        for (i in forecastDay) {

            holder.textDate.text = forecastDay[position].date
            holder.textCondition.text = forecastDay[position].day.condition.text
            val infoText = "${forecastDay[position].day.maxtemp_c}°C/${forecastDay[position].day.mintemp_c}°C"
            holder.textTemp.text = infoText
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