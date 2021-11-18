package com.example.cryptoinvestor.view.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.github.mikephil.charting.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_list.view.*

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
        var imageUrl = "https://static.coincap.io/assets/icons/"
        holder.itemView.CurrencyName.text = rates[position].name
        holder.itemView.currency_current_price.text = PRICE_FORMATTER.format(rates[position].price).toString()
        var changeTxt = rates[position].change24Hr.toString()
        holder.itemView.Currency_percent.text = changeTxt
        Picasso.get().load(imageUrl+rates[position].symbol.lowercase()+"@2x.png").into(holder.itemView.CurrencyIcon)

        if (changeTxt.contains("-")){
            Log.w("Negativ", changeTxt)
            holder.itemView.Currency_percent.setTextColor(Color.RED)
        }else{
            Log.w("Positiv", changeTxt)
            holder.itemView.Currency_percent.setTextColor(Color.GREEN)
        }
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