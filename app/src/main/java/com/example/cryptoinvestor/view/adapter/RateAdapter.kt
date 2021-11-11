package com.example.cryptoinvestor.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.CurrencyListBinding
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.RateDto
import kotlinx.android.synthetic.main.currency_list.view.*
import kotlinx.android.synthetic.main.fragment_buy_crypto.view.*

class RateAdapter : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {
    var onItemClick: ((AssetDto) -> Unit)? = null
    var rates = emptyList<AssetDto>()

    //Laver en inner class her istedet for den nederste chunk kode
    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val assetName = itemView.CurrencyName
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(rates[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {

        //return RateViewHolder(parent)
        return RateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list, parent, false)
        )
    }

    /*
        Her tager vi fat i recyclerViewets data variabler og tildelere dem rates attribut-værdier
     */
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.itemView.CurrencyName.text = rates[position].name
        holder.itemView.currency_current_price.text = rates[position].price.toString()
        holder.itemView.Currency_percent.text = rates[position].change24Hr.toString()
        val rate = rates.get(position)
//        holder.bind(rates.get(position),itemClickListener)
//      bad practice way to start a fragment
//            holder.itemView.setOnClickListener{}
    }

    override fun getItemCount(): Int = rates.size

    fun setData(newList: List<AssetDto>) {
        rates = newList
        notifyDataSetChanged()
    }
//    class RateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
//        CurrencyListBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        ).root
//    ) {
//        private val binding = DataBindingUtil.getBinding<CurrencyListBinding>(itemView)!!
//
//        fun bind(asset: AssetDto) {
//            binding.apply {
//                name = asset.name
//                symbol = asset.id
//                percent = asset.change24Hr.toString()
//                price = asset.price.toString()
//            }
//        }
//    }
}