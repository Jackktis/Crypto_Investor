package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptoinvestor.R
import kotlinx.android.synthetic.main.fragment_buy_and_sell_crypto.*

class BuyAndSellCrypto : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_and_sell_crypto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AmountInQuantity.setText("" + 1)
        val buyBundle = arguments

        if (buyBundle != null) {
            amountInCrypto.setText(buyBundle.getFloat("price").toString())
        }

        amountInCrypto.setOnClickListener(){
            if (buyBundle != null) {
                val amount = (buyBundle.getFloat("price"))
                val quantities = (AmountInQuantity.text.toString()).toFloat()
                val newQuantityAmount = (quantities/amount)
                AmountInQuantity.setText(newQuantityAmount.toString())
            }
        }

        AmountInQuantity.setOnClickListener(){
            if (buyBundle != null) {
                val amount = (buyBundle.getFloat("price"))
                val quantities = (AmountInQuantity.text.toString()).toFloat()
                val newPrice = (quantities * amount)
                amountInCrypto.setText(newPrice.toString())
            }
        }
    }
    }