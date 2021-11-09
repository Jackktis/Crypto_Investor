package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.CurrencyListBinding
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.model.api.dto.RateDto
import kotlinx.android.synthetic.main.currency_list.view.*
import kotlinx.android.synthetic.main.fragment_buy_crypto.view.*

class RateAdapter : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {
    var rates = emptyList<AssetDto>()

    inner class RateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {

        //return RateViewHolder(parent)
        return RateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_list,parent,false))
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.itemView.CurrencyName.text = rates[position].name
        holder.itemView.currency_current_price.text = rates[position].price.toString()
        holder.itemView.Currency_percent.text = rates[position].change24Hr.toString()

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