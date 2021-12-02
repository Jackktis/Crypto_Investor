package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.transactions_list.view.*

/*
  This adapter is almost setup the exact same way CryptoAdapter.kt is, cryptoAdapter even has more.
  I'll keep this class uncommented for that specific reason.
 */
class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.RateViewHolder>() {
    var onItemClick: ((TransactionDto) -> Unit)? = null
    var assetTransactionList = emptyList<TransactionDto>()
    var assets = emptyList<TransactionDto>()


    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            assetTransactionList = assets

            itemView.setOnClickListener {
                onItemClick?.invoke(assetTransactionList[adapterPosition])
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


        if (assetTransactionList[position].action == "Buy") {
            holder.itemView.soldDatetxt.text = "Bought at: "
        }
        if (assetTransactionList[position].action == "Sell") {
            holder.itemView.soldDatetxt.text = "Sold at: "
        }

        holder.itemView.CurrencyNameSold.text = assetTransactionList[position].Currency_Name
        holder.itemView.CurrencySymbolSold.text = assetTransactionList[position].symbol
        holder.itemView.soldDateOutput.text = assetTransactionList[position].Time.toString()
        holder.itemView.quantityPriceSold.text = assetTransactionList[position].price.toString()
        holder.itemView.amountOfQuantities.text = assetTransactionList[position].quantity.toString()

        Picasso.get().load(imageUrl + assetTransactionList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.BoughtCurrencyIcon)

    }

    override fun getItemCount(): Int = assetTransactionList.size

    fun setData(newList: List<TransactionDto>) {
        assets = newList
        assetTransactionList = assets
        notifyDataSetChanged()
    }
}