package com.example.cryptoinvestor.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.cryptoinvestor.databinding.FragmentProfileBinding
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calling our viewmodel to perform network call and observing the result of the network call to set our UI text
        profileViewModel.userBalance.observe(viewLifecycleOwner, {
            view.UserBalanceProfile.text = PRICE_FORMATTER.format(it.toFloat()).toString()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}