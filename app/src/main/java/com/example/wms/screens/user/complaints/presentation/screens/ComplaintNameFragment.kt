package com.example.wms.screens.user.complaints.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentApplyNameBinding

class ComplaintNameFragment : Fragment() {

    private var _binding: FragmentApplyNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplyNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            val name = binding.edName.text.toString()
            if (name.isNotEmpty()) {
                findNavController().navigate(
                    ComplaintNameFragmentDirections.actionComplaintNameFragmentToComplaintConsumerFragment(
                        name
                    )
                )
            }
        }
    }
}
