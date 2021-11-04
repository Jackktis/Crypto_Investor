package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.example.cryptoinvestor.view.adapter.AssetAdapter
import com.example.cryptoinvestor.viewmodel.CryptoViewModel
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel

class CryptoFragment : Fragment() {
    private val adapter by lazy { AssetAdapter() }

    private val viewModel by lazy { cryptoViewModel }
    private var _binding: FragmentCryptoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCryptoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.currencyRV.adapter = adapter
        cryptoViewModel.assets.observe(viewLifecycleOwner, adapter::assets::set)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}