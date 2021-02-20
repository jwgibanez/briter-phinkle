package com.example.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.db.entities.CurrencyInfo
import com.example.demo.databinding.ListItemCurrencyBinding
import java.util.ArrayList

class CurrencyListAdapter(
    val currencies: ArrayList<CurrencyInfo>,
    private val listener: Listener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemCurrencyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.apply {
                currency = currencies[position]
                main.setOnClickListener { listener?.onItemClicked(currency!!) }
            }
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    class ViewHolder(val binding: ListItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {

        fun onItemClicked(currency: CurrencyInfo)
    }
}