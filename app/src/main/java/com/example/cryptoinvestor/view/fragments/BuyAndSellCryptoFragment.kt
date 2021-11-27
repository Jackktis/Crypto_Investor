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

class BuyAndSellCryptoFragment : Fragment() {

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
        quantityCrypto.setText("" + 1.0)
        val buyBundle = arguments

        // Setting the button and text programmatically
        // For future work TODO: learn about stateflows so it isn't necessary
        //                  to send tags through bundle
        if(buyBundle?.getString("tag").equals("Buy")) {
            binding.BuyandSellBT.text = "Buy"
        } else {
            binding.BuyandSellBT.text = "Sell"
        }
        binding.nameCrypto.text = buyBundle?.getString("name").toString()


        if (buyBundle != null) {
            priceCrypto.setText(buyBundle.getFloat("price").toString())
            coinOriginalPrice = (buyBundle.getFloat("price")).toString()
        }

        priceCrypto.setOnClickListener(){
            if (buyBundle != null) {
                updateAmount(priceCrypto.text.toString().toBigDecimal())
            }
        }

        quantityCrypto.setOnClickListener(){
            if (buyBundle != null) {
               updateQuantities(quantityCrypto.text.toString().toBigDecimal())
            }
        }

        BuyandSellBT.setOnClickListener(){
            val coinName = buyBundle?.getString("id").toString()
            val price = priceCrypto.text.toString()
            val quantity = quantityCrypto.text.toString()

            // checking if you are buying or selling
            var action = ""
            if (binding.BuyandSellBT.text.equals("Buy")) {
                action = "bought"

                // register buy transaction in firestore
                viewModel.registerTransaction(coinName, price.toDouble(), quantity.toDouble(),"Buy")
                viewModel.registerBuyTransaction(coinName, price.toDouble() ,quantity.toDouble())

            } else {
                action = "sold"

                // register sell transaction in firestore
                viewModel.registerTransaction(coinName, price.toDouble(), quantity.toDouble(),"Sell")
                viewModel.registerSellTransaction(coinName, price.toDouble(), quantity.toDouble())
            }

            // Dialog box for after completion of transaction
            val dialogBuilder = AlertDialog.Builder(activity!!)
            if (buyBundle != null) {
                dialogBuilder
                    .setMessage("You have " + action + " "+ quantity + " of " + coinName)
                    // if the dialog is cancelable
                    .setCancelable(false)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                        findNavController().navigate(R.id.navigation_Crypto)
                    })
            }
            val alert = dialogBuilder.create()
            alert.setTitle("Transaction complete")
            alert.show()
        }

    }

    fun updateAmount(enteredAmount: BigDecimal){
        val newQuantities: BigDecimal = enteredAmount/coinOriginalPrice.toBigDecimal()
        quantityCrypto.setText(String.format(newQuantities.toString()))
    }

    fun updateQuantities(enteredQuantities: BigDecimal){
        val newQuantities: BigDecimal = coinOriginalPrice.toBigDecimal() * enteredQuantities
        priceCrypto.setText(String.format(newQuantities.toString()))
    }
    }