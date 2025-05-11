package com.example.wms.screens.user.complaints.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentComplaintPhoneBinding

class ComplaintPhoneFragment : Fragment() {

    private var _binding: FragmentComplaintPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComplaintPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = ComplaintPhoneFragmentArgs.fromBundle(requireArguments()).name
        val consumerNumber =
            ComplaintPhoneFragmentArgs.fromBundle(requireArguments()).consumerNumber

        binding.btnContinue.setOnClickListener {
            val phoneNumber = binding.edPhoneNumber.text.toString()
            if (phoneNumber.isNotEmpty()) {
                findNavController().navigate(
                    ComplaintPhoneFragmentDirections.actionComplaintPhoneFragmentToComplaintComplaintFragment(
                        name = name, consumerNumber = consumerNumber, phoneNumber = phoneNumber
                    )
                )
            }
        }
    }
}