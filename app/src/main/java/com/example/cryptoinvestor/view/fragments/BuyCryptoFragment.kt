package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.ActivityAuthBinding.inflate
import com.example.cryptoinvestor.viewmodel.BuyCryptoViewModel

class BuyCryptoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_crypto, container, false)
    }
}