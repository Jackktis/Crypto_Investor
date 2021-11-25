package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cryptoinvestor.R
import kotlinx.android.synthetic.main.fragment_buy_and_sell_crypto.*
import java.math.BigDecimal

class BuyAndSellCrypto : Fragment() {

    var coinOriginalPrice: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_and_sell_crypto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AmountInQuantity.setText("" + 1.0)
        val buyBundle = arguments

        if (buyBundle != null) {
            amountInCrypto.setText(buyBundle.getFloat("price").toString())
            coinOriginalPrice = (buyBundle.getFloat("price")).toString()
        }

        amountInCrypto.setOnClickListener(){
            if (buyBundle != null) {
                updateQuantities(amountInCrypto.text.toString().toBigDecimal())
            }
        }

        AmountInQuantity.setOnClickListener(){
            if (buyBundle != null) {
               updateAmount(AmountInQuantity.text.toString().toBigDecimal())
            }
        }

        BuyandSellBT.setOnClickListener(){
            findNavController().navigate(R.id.Dialog_checkout_fragment, buyBundle)
        }

    }

    fun updateQuantities(enteredAmount: BigDecimal){
        val newQuantities: BigDecimal = enteredAmount/coinOriginalPrice.toBigDecimal()
        AmountInQuantity.setText(String.format(newQuantities.toString()))
    }

    fun updateAmount(enteredQuantities: BigDecimal){
        val newQuantities: BigDecimal = coinOriginalPrice.toBigDecimal() * enteredQuantities
        amountInCrypto.setText(String.format(newQuantities.toString()))
    }
    }