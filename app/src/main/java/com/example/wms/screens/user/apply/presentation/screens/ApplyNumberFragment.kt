package com.example.wms.screens.user.apply.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentApplyNumberBinding

class ApplyNumberFragment : Fragment() {
    private var _binding: FragmentApplyNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplyNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.edPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val phoneNumber = binding.edPhoneNumber.text.toString()
                binding.btnContinue.isEnabled =
                    phoneNumber.length == 11
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        binding.btnContinue.setOnClickListener {
            val phoneNumber = binding.edPhoneNumber.text.toString()
            val name = ApplyNumberFragmentArgs.fromBundle(requireArguments()).name
            val cnic = ApplyNumberFragmentArgs.fromBundle(requireArguments()).cnic

            val action = ApplyNumberFragmentDirections
                .actionApplyNumberFragmentToApplyAddressFragment(
                    name = name,
                    cnic = cnic,
                    number = phoneNumber
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
