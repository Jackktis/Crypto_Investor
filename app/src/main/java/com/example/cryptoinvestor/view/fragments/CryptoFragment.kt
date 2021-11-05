package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel
import com.example.cryptoinvestor.view.adapter.RateAdapter

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
        binding.currencyRV.adapter = adapter
        cryptoViewModel.assets.observe(viewLifecycleOwner, adapter::rates::set)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}