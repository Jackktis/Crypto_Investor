package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.databinding.CurrencyListBinding

class RateAdapter : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {
    var rates: List<CharSequence> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateAdapter.RateViewHolder {
        return RateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RateAdapter.RateViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    override fun getItemCount() = rates.size

    class RateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        CurrencyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root
    ) {
        private val binding = DataBindingUtil.getBinding<CurrencyListBinding>(itemView)!!

        fun bind(rate: CharSequence) {
            binding.name = rate
            binding.symbol = rate
            binding.percent = rate
            binding.price = rate
            binding.executePendingBindings()
        }
    }
}