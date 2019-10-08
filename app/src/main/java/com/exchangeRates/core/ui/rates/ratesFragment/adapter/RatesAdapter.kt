package com.exchangeRates.core.ui.rates.ratesFragment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exchangeRates.R
import com.exchangeRates.core.ext.getCountryIconUrl
import com.exchangeRates.core.ext.inflate
import com.exchangeRates.core.ext.loadFromUrl
import kotlinx.android.synthetic.main.row_rate.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class RatesAdapter
@Inject constructor() : RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    internal var collection: List<RateView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent.inflate(
                R.layout.row_rate
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[viewHolder.adapterPosition])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rateView: RateView) {
            //itemView.tvDate.text = Long.toFormattedDate(rateView.date)
            itemView.tvValue.text = rateView.value.toString()
            itemView.tvCurrency.text = rateView.charCode
            itemView.tvCurrencyName.text = rateView.name
            itemView.countryIcon.loadFromUrl(String.getCountryIconUrl(rateView))
        }
    }
}