package com.example.cryptoinvestor.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.TransactionDto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bought_list.view.*

/*
  This adapter is almost setup the exact same way CryptoAdapter.kt is, cryptoAdapter even has more.
  I'll keep this class uncommented for that specific reason.
 */
class PortfolioBoughtAdapter : RecyclerView.Adapter<PortfolioBoughtAdapter.RateViewHolder>() {

    var onItemClick: ((TransactionDto) -> Unit)? = null

    var assetBoughtList = emptyList<TransactionDto>()
    var assets = emptyList<TransactionDto>()

    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            assetBoughtList = assets

            itemView.setOnClickListener {
                onItemClick?.invoke(assetBoughtList[adapterPosition])
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

        holder.itemView.boughtCurrencyName.text = assetBoughtList[position].Currency_Name
        holder.itemView.boughtCurrencySymbol.text = assetBoughtList[position].symbol
        holder.itemView.boughtPrice.text = assetBoughtList[position].price.toString()
        //ændre her under


        holder.itemView.differencePrice.text = assetBoughtList[position].price.toString()
        holder.itemView.bought_quantities.text = assetBoughtList[position].quantity.toString()
        Picasso.get().load(imageUrl + assetBoughtList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.BoughtCurrencyIcon)

    }

    override fun getItemCount(): Int = assetBoughtList.size

    fun setDataForBought(newList: List<TransactionDto>) {
        assets = newList
        assetBoughtList = assets
        notifyDataSetChanged()
    }
}