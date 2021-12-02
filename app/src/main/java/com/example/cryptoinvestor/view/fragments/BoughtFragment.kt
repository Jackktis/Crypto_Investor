package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cryptoinvestor.databinding.FragmentBoughtBinding
import com.example.cryptoinvestor.view.adapter.BoughtAdapter
import com.example.cryptoinvestor.viewmodel.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoughtFragment : Fragment() {
    private val portfolioViewModel: PortfolioViewModel by viewModels()
    private lateinit var binding: FragmentBoughtBinding
    private val adapter by lazy { BoughtAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoughtBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.adapter = adapter

        portfolioViewModel.getBought()

        portfolioViewModel.boughtList.observe(viewLifecycleOwner, { boughs ->

            adapter.setDataForBought(boughs)

        })
    }
}