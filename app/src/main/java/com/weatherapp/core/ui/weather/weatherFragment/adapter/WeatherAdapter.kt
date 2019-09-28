package com.weatherapp.core.ui.weather.weatherFragment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.core.ext.getWeatherIconUrl
import com.weatherapp.core.ext.inflate
import com.weatherapp.core.ext.loadFromUrl
import com.weatherapp.core.ext.toFormattedDate
import com.weatherapp.core.navigation.Navigator
import kotlinx.android.synthetic.main.row_weather.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class WeatherAdapter
@Inject constructor() : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    internal var collection: List<WeatherView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (WeatherView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent.inflate(
                R.layout.row_weather
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[viewHolder.adapterPosition], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherView: WeatherView, clickListener: (WeatherView, Navigator.Extras) -> Unit) {
            itemView.tvDate.text = Long.toFormattedDate(weatherView.date)
            itemView.tvCity.text = weatherView.cityName
            itemView.tvTemp.text = String.format(
                itemView.context.resources.getString(R.string.temperature_value),
                weatherView.currentTemp
            )
            itemView.tvSkyCondition.text = weatherView.skyCondition
            itemView.weatherIcon.loadFromUrl(String.getWeatherIconUrl(weatherView))
            ViewCompat.setTransitionName(
                itemView.weatherIcon,
                adapterPosition.toString() + "_image"
            )
            itemView.setOnClickListener {
                clickListener(
                    weatherView,
                    Navigator.Extras(itemView.weatherIcon)
                )
            }
        }
    }
}