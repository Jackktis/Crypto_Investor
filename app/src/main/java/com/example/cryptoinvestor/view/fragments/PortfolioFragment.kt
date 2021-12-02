package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoinvestor.databinding.FragmentPortfolioBinding
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.view.adapter.PortfolioBoughtAdapter
import com.example.cryptoinvestor.viewmodel.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_portfolio.*
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private val portfolioViewModel: PortfolioViewModel by viewModels()
    private var binding: FragmentPortfolioBinding? = null
    private val adapter by lazy { PortfolioBoughtAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewForBought()
        portfolioViewModel.getBought()
        portfolioViewModel.boughtList.observe(viewLifecycleOwner, { bought ->
            adapter.setDataForBought(bought)
        })

        portfolioViewModel.userBalance.observe(viewLifecycleOwner, {
            view.UserBalancePortfolio.text = PRICE_FORMATTER.format(it.toFloat()).toString()
        })
    }

    private fun setupRecyclerViewForBought() {
        bought_currency_RV.adapter = adapter
        bought_currency_RV.layoutManager = LinearLayoutManager(context)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}