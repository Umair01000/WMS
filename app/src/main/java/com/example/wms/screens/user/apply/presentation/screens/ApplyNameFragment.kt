package com.example.wms.screens.user.apply.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentApplyNameBinding

class ApplyNameFragment : Fragment() {
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
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?, start: Int, before: Int, count: Int
            ) {
                val isNameNotEmpty = !binding.edName.text.isNullOrEmpty()
                binding.btnContinue.isEnabled = isNameNotEmpty
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        binding.btnContinue.setOnClickListener {
            val name = binding.edName.text.toString() // Get the name from the EditText
            if (name.isNotEmpty()) {
                findNavController().navigate(
                    ApplyNameFragmentDirections.actionApplyNameFragmentToApplyCnicFragment(name)
                )
            }
        }
    }
}