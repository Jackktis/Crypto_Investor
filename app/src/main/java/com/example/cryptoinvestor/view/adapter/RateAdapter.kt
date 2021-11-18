package com.example.cryptoinvestor.view.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.github.mikephil.charting.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class RateAdapter : RecyclerView.Adapter<RateAdapter.RateViewHolder>(), Filterable {
    var charSearch = ""
    var onItemClick: ((AssetDto) -> Unit)? = null
    var rates = emptyList<AssetDto>()
    var assetFilterList = emptyList<AssetDto>()


    //Laver en inner class her istedet for den nederste chunk kode
    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            assetFilterList = rates

            itemView.setOnClickListener {
                onItemClick?.invoke(rates[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list, parent, false)
        )
    }

    /*
        Her tager vi fat i recyclerViewets data variabler og tildelere dem rates attribut-værdier
     */
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        var imageUrl = "https://static.coincap.io/assets/icons/"
        holder.itemView.CurrencyName.text = assetFilterList[position].name
        holder.itemView.currency_current_price.text =
            PRICE_FORMATTER.format(assetFilterList[position].price).toString()
        var changeTxt = assetFilterList[position].change24Hr.toString()
        holder.itemView.Currency_percent.text = changeTxt
        Picasso.get().load(imageUrl + assetFilterList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.CurrencyIcon)

        //Changing the color of the changeTxt according to being negative or positive.
        if (changeTxt.contains("-")) {
            Log.w("Negativ", changeTxt)
            holder.itemView.Currency_percent.setTextColor(Color.RED)
        } else {
            Log.w("Positiv", changeTxt)
            holder.itemView.Currency_percent.setTextColor(Color.GREEN)
        }


    }

    override fun getItemCount(): Int = assetFilterList.size

    /*
        Gets called from the CryptoFragment, sets the data of the adapter
     */
    fun setData(newList: List<AssetDto>) {
        rates = newList

        /*
        Crucial, this sets the filter to empty when the initial data gets set,
        removing this line would result in the recyclerview being empty from start
         */
        filter.filter(charSearch)
        notifyDataSetChanged()
    }

    /*
        Search filter function, we define the class of Filter
        Does the charSearch contain anything
            - then match the chars to the names of the objects in list of AssetDto's
             - add them to the resultList
             - add the rows from resultList to assetFilter
             - Create a FilterResults object and add the assetFilterList to the object and return
             - At last publish the filter results and notify the adapter, which shows the searched items in the recyclerview
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charSearch = constraint.toString()
                Log.d("TAG", charSearch)
                if (charSearch.isEmpty()) {
                    assetFilterList = rates
                } else {
                    val resultList = emptyList<AssetDto>().toMutableList()
                    for (row in assetFilterList) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    assetFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = assetFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                assetFilterList = results?.values as List<AssetDto>
                notifyDataSetChanged()
            }
        }
    }
}