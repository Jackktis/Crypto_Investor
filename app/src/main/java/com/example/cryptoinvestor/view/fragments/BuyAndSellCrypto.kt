package com.example.cryptoinvestor.view.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.FragmentBuyAndSellCryptoBinding
import com.example.cryptoinvestor.databinding.FragmentBuyOrSellPopUpBinding
import com.example.cryptoinvestor.di.ServiceLocator.buyAndSellViewModel
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel
import com.example.cryptoinvestor.viewmodel.BuyAndSellViewModel
import kotlinx.android.synthetic.main.fragment_buy_and_sell_crypto.*
import kotlinx.android.synthetic.main.fragment_buy_or_sell_pop_up.*
import java.math.BigDecimal

class BuyAndSellCrypto : Fragment() {

    var coinOriginalPrice: String = ""
    var changedPrice: Float = 0.0f

    private val viewModel by lazy { buyAndSellViewModel }

    private lateinit var binding: FragmentBuyAndSellCryptoBinding
    private lateinit var bindingPopUp: FragmentBuyOrSellPopUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyAndSellCryptoBinding.inflate(inflater, container, false)
        bindingPopUp = FragmentBuyOrSellPopUpBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
        //inflater.inflate(R.layout.fragment_buy_and_sell_crypto, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
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
                updateAmount(amountInCrypto.text.toString().toBigDecimal())
            }
        }

        AmountInQuantity.setOnClickListener(){
            if (buyBundle != null) {
               updateQuantities(AmountInQuantity.text.toString().toBigDecimal())
            }
        }

        BuyandSellBT.setOnClickListener(){
            val coinName = buyBundle?.getString("id").toString()
            val price = amountInCrypto.text.toString()
            val quantity = AmountInQuantity.text.toString()

            // register transaction in firestore
            viewModel.registerBuyTransaction(coinName, price.toDouble() ,quantity.toDouble())

            // Dialog box for after completion of transaction
            val dialogBuilder = AlertDialog.Builder(activity!!)
            if (buyBundle != null) {
                dialogBuilder
                    .setMessage("You have bought " + quantity + " of " + coinName)
                    // if the dialog is cancelable
                    .setCancelable(false)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                        findNavController().navigate(R.id.InfoCryptoFragment, buyBundle)

                    })
            }
            val alert = dialogBuilder.create()
            alert.setTitle("Transaction complete")
            alert.show()
        }

    }

    fun updateAmount(enteredAmount: BigDecimal){
        val newQuantities: BigDecimal = enteredAmount/coinOriginalPrice.toBigDecimal()
        AmountInQuantity.setText(String.format(newQuantities.toString()))
    }

    fun updateQuantities(enteredQuantities: BigDecimal){
        val newQuantities: BigDecimal = coinOriginalPrice.toBigDecimal() * enteredQuantities
        amountInCrypto.setText(String.format(newQuantities.toString()))
    }
    }