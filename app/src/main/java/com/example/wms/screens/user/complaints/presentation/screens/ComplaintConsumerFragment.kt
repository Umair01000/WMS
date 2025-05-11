package com.example.wms.screens.user.complaints.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentComplaintConsumerBinding

class ComplaintConsumerFragment : Fragment() {

    private var _binding: FragmentComplaintConsumerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComplaintConsumerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = ComplaintConsumerFragmentArgs.fromBundle(requireArguments()).name

        binding.btnContinue.setOnClickListener {
            val consumerNumber = binding.edConsumerNumber.text.toString()
            if (consumerNumber.isNotEmpty()) {
                findNavController().navigate(
                    ComplaintConsumerFragmentDirections.actionComplaintConsumerFragmentToComplaintPhoneFragment(
                        name = name, consumerNumber = consumerNumber
                    )
                )
            }
        }
    }
}
