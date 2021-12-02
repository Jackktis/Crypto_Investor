package com.example.cryptoinvestor.view.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.FragmentBuyAndSellCryptoBinding
import com.example.cryptoinvestor.viewmodel.BuyAndSellViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_buy_and_sell_crypto.*
import java.math.BigDecimal

@AndroidEntryPoint
class BuyAndSellCryptoFragment : Fragment() {
    private val viewModel: BuyAndSellViewModel by viewModels()
    private lateinit var binding: FragmentBuyAndSellCryptoBinding
    private var coinOriginalPrice: String =
        "" //String for holding the original price of a crypto, will be used for exchange rates

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyAndSellCryptoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quantityCrypto.setText("" + 1.0)
        val buyBundle = arguments

        // Setting the button and text programmatically
        // For future work TODO: learn about stateflows so it isn't necessary
        //                  to send tags through bundle
        if (buyBundle?.getString("tag").equals("Buy")) {
            binding.BuyandSellBT.text = "Buy"
        } else {
            binding.BuyandSellBT.text = "Sell"
        }
        binding.nameCrypto.text = buyBundle?.getString("name").toString()


        if (buyBundle != null) {
            priceCrypto.setText(buyBundle.getFloat("price").toString())
            coinOriginalPrice = (buyBundle.getFloat("price")).toString()
        }

        priceCrypto.setOnClickListener() {
            if (buyBundle != null) {
                updateQuantityAccordingToPrice(priceCrypto.text.toString().toBigDecimal())
            }
        }

        quantityCrypto.setOnClickListener() {
            if (buyBundle != null) {
                updatePriceAccordingToQuantity(quantityCrypto.text.toString().toBigDecimal())
            }
        }

        BuyandSellBT.setOnClickListener() {
            val coinName = buyBundle?.getString("id").toString()
            val symbol = buyBundle?.getString("symbol").toString()
            val price = priceCrypto.text.toString()
            val quantity = quantityCrypto.text.toString()

            // checking if you are buying or selling
            val action: String
            if (binding.BuyandSellBT.text.equals("Buy")) {
                action = "bought"

                // register buy transaction in firestore
                viewModel.registerTransaction(
                    coinName,
                    symbol,
                    price.toDouble(),
                    quantity.toDouble(),
                    "Buy"
                )
                viewModel.registerBuyTransaction(coinName, price.toDouble(), quantity.toDouble())

                transactionDialog(action, quantity, coinName, buyBundle)

            } else {
                action = "sold"

                //Try make network call to register a transaction
                try {
                    viewModel.registerSellTransaction(
                        coinName,
                        price.toDouble(),
                        quantity.toDouble()
                    )
                    viewModel.quantityMessageObserver.observe(viewLifecycleOwner, {
                        //If quantityMessageObserver == true, that means that the user has enough quantity of the crypto to sell
                        if (it == true) {
                            viewModel.registerTransaction(
                                coinName,
                                symbol,
                                price.toDouble(),
                                quantity.toDouble(),
                                "Sell"
                            )
                            transactionDialog(action, quantity, coinName, buyBundle)
                        } else {
                            Toast.makeText(
                                activity,
                                "You do not own enough $coinName",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }

    }

    private fun updateQuantityAccordingToPrice(priceForQuantity: BigDecimal) {
        val quantityAccordingToPrice: BigDecimal =
            priceForQuantity / coinOriginalPrice.toBigDecimal()
        quantityCrypto.setText(String.format(quantityAccordingToPrice.toString()))
    }

    private fun updatePriceAccordingToQuantity(quantityEntered: BigDecimal) {
        val priceAccordingToQuantity: BigDecimal =
            coinOriginalPrice.toBigDecimal() * quantityEntered
        priceCrypto.setText(String.format(priceAccordingToQuantity.toString()))
    }

    /*
    Creation of a dialog with the transaction info
     */
    private fun transactionDialog(
        action: String,
        quantity: String,
        coinName: String,
        buyBundle: Bundle?
    ) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        if (buyBundle != null) {
            dialogBuilder
                .setMessage("You have $action $quantity of $coinName")
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