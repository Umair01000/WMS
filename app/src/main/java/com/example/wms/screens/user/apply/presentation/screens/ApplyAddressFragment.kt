package com.example.wms.screens.user.apply.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentApplyAddressBinding
import com.example.wms.screens.user.apply.domain.model.ApplyData
import com.example.wms.screens.user.apply.domain.repository.ApplyResult
import com.example.wms.screens.user.apply.presentation.ApplyViewModel
import com.example.wms.screens.user.apply.presentation.di.ApplyModule

class ApplyAddressFragment : Fragment() {

    private var _binding: FragmentApplyAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApplyViewModel = ApplyModule.provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplyAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.applicationResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ApplyResult.Success -> {
                    showLoading(false)
                    findNavController().navigate(ApplyAddressFragmentDirections.actionApplyAddressFragmentToApplySuccessFragment())
                }

                is ApplyResult.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }

                ApplyResult.Loading -> showLoading(true)
            }
        })

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.edAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?, start: Int, before: Int, count: Int
            ) {
                val isAddressValid = (charSequence?.length ?: 0) > 5
                binding.btnApply.isEnabled = isAddressValid
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        binding.btnApply.setOnClickListener {
            val name = ApplyAddressFragmentArgs.fromBundle(requireArguments()).name
            val cnic = ApplyAddressFragmentArgs.fromBundle(requireArguments()).cnic
            val number = ApplyAddressFragmentArgs.fromBundle(requireArguments()).number
            val address = binding.edAddress.text.toString()
            if (address.isNotEmpty()) {
                val deviceId = Settings.Secure.getString(
                    requireContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                )

                val applyData = ApplyData(name, cnic, number, address, deviceId)
                viewModel.applyForService(applyData)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                edAddress.isEnabled = false
                btnApply.isEnabled = false
                backButton.isEnabled = false
                progressBar.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                edAddress.isEnabled = true
                btnApply.isEnabled = true
                backButton.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}