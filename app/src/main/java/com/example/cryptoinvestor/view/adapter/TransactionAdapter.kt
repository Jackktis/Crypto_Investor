package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.transactions_list.view.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.RateViewHolder>(){
    var onItemClick: ((TransactionDto) -> Unit)? = null
    var transactionFilterList = emptyList<TransactionDto>()
    var rates = emptyList<TransactionDto>()


    //Laver en inner class her istedet for den nederste chunk kode
    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            transactionFilterList = rates

            itemView.setOnClickListener {
                onItemClick?.invoke(transactionFilterList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.transactions_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        var imageUrl = "https://static.coincap.io/assets/icons/"

        var date = transactionFilterList[position].Time

        println("____________________________________")
        println(date)
        date
        println(date)
        println("____________________________________")

        holder.itemView.CurrencyNameSold.text = transactionFilterList[position].Currency_Name
        holder.itemView.CurrencySymbolSold.text = transactionFilterList[position].symbol
        holder.itemView.soldDateOutput.text = date
        holder.itemView.quantityPriceSold.text = transactionFilterList[position].price.toString()
        holder.itemView.amountOfQuantities.text = transactionFilterList[position].quantity.toString()

        Picasso.get().load(imageUrl + transactionFilterList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.BoughtCurrencyIcon)

    }

    override fun getItemCount(): Int = transactionFilterList.size

    fun setData(newList: List<TransactionDto>) {
        rates = newList
        transactionFilterList = rates
        notifyDataSetChanged()
    }
}