package com.example.wms.screens.user.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentUserHomeBinding

class UserHomeFragment : Fragment() {
    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnWaterBill.setOnClickListener {
                findNavController().navigate(UserHomeFragmentDirections.actionUserHomeFragmentToBillCheckFragment())
            }
            btnHelpline.setOnClickListener {
                findNavController().navigate(UserHomeFragmentDirections.actionUserHomeFragmentToFragmentHelpline())
            }
            btnLocation.setOnClickListener {
                findNavController().navigate(UserHomeFragmentDirections.actionUserHomeFragmentToLocationFragment())
            }
            btnApplyMeter.setOnClickListener {
                findNavController().navigate(UserHomeFragmentDirections.actionUserHomeFragmentToApplyConditionsFragment())
            }
            btnComplains.setOnClickListener {
                findNavController().navigate(UserHomeFragmentDirections.actionUserHomeFragmentToComplaintNameFragment())
            }
        }
    }
}