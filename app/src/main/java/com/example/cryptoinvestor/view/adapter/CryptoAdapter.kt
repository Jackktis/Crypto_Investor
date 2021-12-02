package com.example.cryptoinvestor.view.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.model.api.dto.AssetDto
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_list.view.*
import java.util.*

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.RateViewHolder>(), Filterable {
    var charSearch = ""
    var onItemClick: ((AssetDto) -> Unit)? = null
    /*
    Initializing two empty lists
        - assets used for containing the new list of assets injected by the viewModel
        - assetFilterList used for containing the newly retrieved list assets and later modified if the user requires to search in the list (the list that gets displayed)
     */
    var assets = emptyList<AssetDto>()
    var assetFilterList = emptyList<AssetDto>()



    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //Sets the assetFilterList to be equal to rates
            assetFilterList = assets

            itemView.setOnClickListener {
                onItemClick?.invoke(assetFilterList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list, parent, false)
        )
    }

    /*
        Her tager vi fat i recyclerViewets data variabler og tildelere dem assets attribut-værdier
     */
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        //imageUrl contains the start of the URL for the crypto icons
        var imageUrl = "https://static.coincap.io/assets/icons/"
        holder.itemView.CurrencyName.text = assetFilterList[position].name
        holder.itemView.CurrencySymbol.text = assetFilterList[position].symbol
        holder.itemView.CurrencyPrice.text =
            PRICE_FORMATTER.format(assetFilterList[position].price).toString()
        var changeTxt = assetFilterList[position].change24Hr.toString()
        holder.itemView.CurrencyPercent.text = changeTxt
        /*
          We are using the 3rd party library Picasso for getting the crypto icons from paths and injecting them into the UI
            we build the path from the imageUrl and the crypto's "symbol" e.g. "btc", and at last the scaling format
         */
        Picasso.get().load(imageUrl + assetFilterList[position].symbol.lowercase() + "@2x.png")
            .into(holder.itemView.CurrencyIcon)

        //Changing the color of the changeTxt according to being negative or positive.
        if (changeTxt.contains("-")) {
            //Log.w("Negativ", changeTxt)
            holder.itemView.CurrencyPercent.setTextColor(Color.RED)
        } else {
            //Log.w("Positiv", changeTxt)
            holder.itemView.CurrencyPercent.setTextColor(Color.GREEN)
        }
    }


    override fun getItemCount(): Int = assetFilterList.size

    /*
        Gets called from the CryptoFragment, sets the data of the adapter
     */
    fun setData(newList: List<AssetDto>) {
        assets = newList

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


     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charSearch = constraint.toString()
                //Does the charSearch contain anything
                if (charSearch.isEmpty()) {
                    //If it doesn't just fill it with the list acquired by the viewModel
                    assetFilterList = assets
                } else {
                    //If it does then we make a new empty list of AssetDTO, that we convert to mutable
                    val resultList = emptyList<AssetDto>().toMutableList()
                    //for every crypto in the crypto list
                    for (row in assetFilterList) {
                        // if the chars in the search field match the name of the object in the row of the cryptos list
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            //then add the object of crypto to the result
                            resultList.add(row)
                        }
                    }
                    //Now we add all the results that matched the search input to the list that's responsible for what is shown
                    assetFilterList = resultList
                }
                //Create a FilterResults object which is designated to hold the values of the filtering operation
                val filterResults = FilterResults()
                //We add the list of the search-matched cryptos to the filterResults
                filterResults.values = assetFilterList
                //We return it so we can return it!
                return filterResults
            }
            //At last publish the filter results and notify the adapter, which shows the searched items in the recyclerview, voilà!
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                assetFilterList = results?.values as List<AssetDto>
                notifyDataSetChanged()
            }
        }
    }
}