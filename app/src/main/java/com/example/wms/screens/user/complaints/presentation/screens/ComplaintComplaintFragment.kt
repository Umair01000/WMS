package com.example.wms.screens.user.complaints.presentation.screens

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
import com.example.wms.databinding.FragmentComplaintComplaintBinding
import com.example.wms.screens.user.complaints.domain.model.ComplaintData
import com.example.wms.screens.user.complaints.domain.repository.ComplaintResult
import com.example.wms.screens.user.complaints.presentation.ComplaintViewModel
import com.example.wms.screens.user.complaints.presentation.di.ComplaintModule

class ComplaintComplaintFragment : Fragment() {

    private var _binding: FragmentComplaintComplaintBinding? = null
    private val binding get() = _binding!!

    private var viewModel: ComplaintViewModel = ComplaintModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComplaintComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = ComplaintComplaintFragmentArgs.fromBundle(requireArguments()).name
        val consumerNumber =
            ComplaintComplaintFragmentArgs.fromBundle(requireArguments()).consumerNumber
        val phoneNumber = ComplaintComplaintFragmentArgs.fromBundle(requireArguments()).phoneNumber

        binding.btnSubmit.isEnabled = false

        binding.edComplaint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Not needed for now, but can be used to handle pre-change events.
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val complaintText = charSequence.toString()
                binding.btnSubmit.isEnabled = complaintText.length > 10
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        viewModel.complaintResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ComplaintResult.Success -> {
                    findNavController().navigate(
                        ComplaintComplaintFragmentDirections.actionComplaintComplaintFragmentToComplaintSuccessFragment()
                    )
                }

                is ComplaintResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }

                ComplaintResult.Loading -> {
                    showLoading(true)
                }
            }
        })
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSubmit.setOnClickListener {
            val complaint = binding.edComplaint.text.toString()
            if (complaint.isNotEmpty()) {
                val deviceId = Settings.Secure.getString(
                    requireContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                val complaintData =
                    ComplaintData(name, consumerNumber, phoneNumber, complaint, deviceId)
                viewModel.submitComplaint(complaintData)
            } else {
                Toast.makeText(requireContext(), "Please enter a complaint", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.btnSubmit.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
