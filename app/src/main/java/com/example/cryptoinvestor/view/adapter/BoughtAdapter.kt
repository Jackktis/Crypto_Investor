package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.CoinCapApi
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bought_list.view.*

class BoughtAdapter : RecyclerView.Adapter<BoughtAdapter.RateViewHolder>(){
    var onItemClick: ((TransactionDto) -> Unit)? = null
    var boughtFilterList = emptyList<TransactionDto>()
    var rates = emptyList<TransactionDto>()

    //Laver en inner class her istedet for den nederste chunk kode
    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            boughtFilterList = rates

            itemView.setOnClickListener {
                onItemClick?.invoke(boughtFilterList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.bought_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        var imageUrl = "https://static.coincap.io/assets/icons/"

        //TODO("implement API kald, så den minuser med værdien du købt den for")

        holder.itemView.boughtCurrencyName.text = boughtFilterList[position].Currency_Name
        holder.itemView.boughtCurrencySymbol.text = boughtFilterList[position].symbol
        holder.itemView.boughtPrice.text = boughtFilterList[position].price.toString()
        //ændre her under


        holder.itemView.differencePrice.text = boughtFilterList[position].price.toString()
        holder.itemView.bought_quantities.text = boughtFilterList[position].quantity.toString()
        Picasso.get().load(imageUrl + boughtFilterList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.BoughtCurrencyIcon)

    }

    override fun getItemCount(): Int = boughtFilterList.size

    fun setDataForBought(newList: List<TransactionDto>) {
        rates = newList
        boughtFilterList = rates
        notifyDataSetChanged()
    }
}