package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.databinding.CurrencyListBinding

class AssetAdapter : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {
    var assets: List<CharSequence> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetAdapter.AssetViewHolder {
        return AssetViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AssetAdapter.AssetViewHolder, position: Int) {
        holder.bind(assets[position])
    }

    override fun getItemCount() = assets.size

    class AssetViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
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