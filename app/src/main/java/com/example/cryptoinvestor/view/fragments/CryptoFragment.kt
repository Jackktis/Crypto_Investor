package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel
import com.example.cryptoinvestor.view.adapter.RateAdapter
import kotlinx.android.synthetic.main.fragment_crypto.*

class CryptoFragment : Fragment() {
    private val viewModel by lazy { cryptoViewModel }
    private val adapter by lazy { RateAdapter() }

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
           Grabbing the list of 10 coins(Response List of AssetDto from the api-call)
           and setting the data to the adapter.
         */
        viewModel.getTenAssets()
        viewModel.assetsList.observe(viewLifecycleOwner, { assets ->
            if (assets.isSuccessful) {
                assets.body()?.let {
                    println(assets.body()?.toString())
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

        adapter.onItemClick = { assets ->
            val bundle = bundleOf("id" to assets.id)

            findNavController().navigate(R.id.InfoCryptoFragment, bundle)


            // Used only if you are accessing a very specific/custom fragment
            // using the navcontroller and navgraph is favored
            /*
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack("crypto_list")
                .replace(R.id.container, infoCryptoFragment)
                .commit()
            */
        }

    }
}