package com.example.wms.screens.admin.complaints.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wms.databinding.FragmentAdminComplaintsBinding
import com.example.wms.screens.admin.complaints.presentation.AdminComplaintsViewModel
import com.example.wms.screens.admin.complaints.presentation.adapter.ComplaintsAdapter
import com.example.wms.screens.admin.complaints.presentation.di.AdminComplaintsModule

class AdminComplaintsFragment : Fragment() {

    private var _binding: FragmentAdminComplaintsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminComplaintsViewModel = AdminComplaintsModule.provideViewModel()
    private val complaintsAdapter = ComplaintsAdapter(onComplaintClick = {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminComplaintsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up RecyclerView layout manager
        binding.complaintsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.complaintsRecyclerView.adapter = complaintsAdapter

        // Observe complaints list
        viewModel.complaintsList.observe(viewLifecycleOwner, Observer { complaints ->
            complaints?.let {
                complaintsAdapter.submitList(it)  // Update adapter with new data
                Log.d("AdminComplaintsFragment", "Received complaints: $it")  // Log the received data
            }
        })

        // Handle phone number input text changes
        binding.edPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNumber = s.toString()
                viewModel.filterComplaintsByPhone(phoneNumber)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

