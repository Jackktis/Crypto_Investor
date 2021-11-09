package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel
import com.example.cryptoinvestor.view.adapter.RateAdapter
import kotlinx.android.synthetic.main.fragment_crypto.*

class CryptoFragment : Fragment() {
    companion object {
        fun newInstance() = CryptoFragment()
    }

    private val viewModel by lazy { cryptoViewModel }
    private val adapter by lazy { RateAdapter() }

    private lateinit var binding: FragmentCryptoBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

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

        viewModel.getTenAssets()
        viewModel.assetsList.observe(viewLifecycleOwner, { assets ->
            if (assets.isSuccessful){
                assets.body()?.let {
                    println(assets.body()?.toString())
                    adapter.setData(it)
//                    Log.d("Response", it.name)
//                    Log.d("Response", it.id)
//                    Log.d("Response", it.price.toString())
//                    Log.d("Response", it.change24Hr.toString())
//                    Log.d("Response", it.volume24Hr.toString())

                }
            }else{
                Log.d("Response", assets.errorBody().toString())

            }


        })
    }

    private fun setupRecyclerView(){
        currency_RV.adapter = adapter
        currency_RV.layoutManager = LinearLayoutManager(context)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}