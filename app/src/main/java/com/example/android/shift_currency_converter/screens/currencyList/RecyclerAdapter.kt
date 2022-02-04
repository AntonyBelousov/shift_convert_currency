package com.example.android.shift_currency_converter.screens.currencyList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shift_currency_converter.R
import com.example.android.shift_currency_converter.model.CurrencyList
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    lateinit var currency: CurrencyList
    private var df: DecimalFormat = DecimalFormat("00.0000", DecimalFormatSymbols(Locale.GERMAN))

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyPair: TextView = itemView.findViewById(R.id.tv_currency_char_code)
        val currencyNominal: TextView = itemView.findViewById(R.id.tv_currency_nominal)
        val currencyRate: TextView = itemView.findViewById(R.id.tv_currency_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currencyCharCode: String = currency.valute.keys.elementAt(position).toString()
        holder.currencyPair.text = currency.valute.keys.elementAt(position).toString()
        holder.currencyRate.text = df.format(currency.valute.get(currencyCharCode)?.value)
        holder.currencyNominal.text = currency.valute.get(currencyCharCode)?.nominal.toString()
    }

    override fun getItemCount(): Int {
        return currency.valute.size
    }

    fun updateData(currency: CurrencyList) {
        this.currency = currency
    }
}