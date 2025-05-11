package com.example.wms.screens.user.apply.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentApplyCnicBinding

class ApplyCnicFragment : Fragment() {
    private var _binding: FragmentApplyCnicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplyCnicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = ApplyCnicFragmentArgs.fromBundle(requireArguments()).name

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.edCnic.addTextChangedListener(object : TextWatcher {
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
                val cnic = binding.edCnic.text.toString()
                val formattedCnic = formatCnic(cnic)
                if (cnic != formattedCnic) {
                    binding.edCnic.setText(formattedCnic)
                    binding.edCnic.setSelection(formattedCnic.length) // Move the cursor to the end of the text
                }
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        binding.btnContinue.setOnClickListener {
            val cnic = binding.edCnic.text.toString()
            if (cnic.isNotEmpty()) {
                findNavController().navigate(
                    ApplyCnicFragmentDirections.actionApplyCnicFragmentToApplyNumberFragment(
                        name = name,
                        cnic = cnic
                    )
                )
            }
        }
    }

    private fun formatCnic(cnic: String): String {
        var formattedCnic = cnic.replace("-", "") // Remove any existing dashes

        // Format the CNIC string when the length is greater than or equal to 5
        if (formattedCnic.length >= 5) {
            val firstPart = formattedCnic.substring(0, 5)
            val secondPart = if (formattedCnic.length >= 12) formattedCnic.substring(5, 12) else formattedCnic.substring(5)
            val thirdPart = if (formattedCnic.length > 12) formattedCnic.substring(12) else ""

            // Construct the CNIC in the correct format
            formattedCnic = "$firstPart-$secondPart-$thirdPart"
        }

        return formattedCnic
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}