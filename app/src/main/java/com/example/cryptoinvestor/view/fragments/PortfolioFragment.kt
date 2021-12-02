package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptoinvestor.databinding.FragmentPortfolioBinding
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.view.adapter.PortfolioBoughtAdapter
import com.example.cryptoinvestor.viewmodel.PortfolioViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        TabLayoutMediator(tablayout, bought_currency_VP) { tab, position ->
            if(position == 0){
                tab.setText("Favourites")
            }else if (position == 1){
                tab.setText("Bought")
            }
        }.attach()


        portfolioViewModel.userBalance.observe(viewLifecycleOwner, {
            view.UserBalancePortfolio.text = PRICE_FORMATTER.format(it.toFloat()).toString()
        })
}
    private fun setupRecyclerViewForBought() {
        bought_currency_VP.adapter = Adapter(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

class Adapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if(position == 1) {
            return BoughtFragment()
        }else
            return FavouritesFragment()
    }
}