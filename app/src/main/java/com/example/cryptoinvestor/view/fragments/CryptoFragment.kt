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

class CryptoFragment : Fragment() {
    private val adapter by lazy { AssetAdapter() }

    private lateinit var cryptoViewModel: CryptoViewModel
    private var _binding: FragmentCryptoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cryptoViewModel =
            ViewModelProvider(this).get(CryptoViewModel::class.java)

        _binding = FragmentCryptoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
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