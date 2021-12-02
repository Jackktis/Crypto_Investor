package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.view.adapter.CryptoAdapter
import com.example.cryptoinvestor.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto.*
import kotlinx.android.synthetic.main.fragment_crypto.view.*

@AndroidEntryPoint
class CryptoFragment : Fragment() {
    //private val cryptoViewModel by lazy { this.cryptoViewModel }
    private val cryptoViewModel: CryptoViewModel by viewModels()
    private val adapter by lazy { CryptoAdapter() }

    private lateinit var binding: FragmentCryptoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        /*
           Grabbing the list of 100 coins(Response List of AssetDto from the api-call)
           and setting the data to the adapter.
         */
        cryptoViewModel.userBalance.observe(viewLifecycleOwner, {
            view.UserBalanceCrypto.text = PRICE_FORMATTER.format(it.toFloat()).toString()
        })

        cryptoViewModel.getHundredAssets()
        //We observe the changes of the list that contain the API response
        cryptoViewModel.assetsList.observe(viewLifecycleOwner, { assets ->
            //When assets is retrieved, we fill the adapter with the list of data
            if (assets.isSuccessful) {
                assets.body()?.let {
                    adapter.setData(it)
                }
            } else {
                Log.d("Response", assets.errorBody().toString())
            }
        })

        /*
          Gets called every time we type in the SearchView, and updates the Recyclerview accordingly
       */
        rateSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        currency_RV.adapter = adapter
        currency_RV.layoutManager = LinearLayoutManager(context)

        //When user clicks on item in list, we navigate to a InfoCryptoFragment for the desired crypto, we do that by injecting the ID into the fragment from a bundle
        adapter.onItemClick = { assets ->
            val bundle = bundleOf("id" to assets.id)
            findNavController().navigate(R.id.InfoCryptoFragment, bundle)
        }

    }
}