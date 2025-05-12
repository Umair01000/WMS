package com.example.wms.screens.admin.meterrequests.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wms.databinding.FragmentAdminMeterRequestBinding
import com.example.wms.screens.admin.meterrequests.presentation.adapter.MeterRequestsAdapter
import com.example.wms.screens.admin.meterrequests.presentation.di.AdminMeterRequestModule

class AdminMeterRequestFragment : Fragment() {

    private var _binding: FragmentAdminMeterRequestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminMeterRequestViewModel = AdminMeterRequestModule.provideViewModel()
    private val meterRequestsAdapter = MeterRequestsAdapter(onApplicationClick = {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminMeterRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.meterRequestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.meterRequestsRecyclerView.adapter = meterRequestsAdapter

        viewModel.meterApplicationsList.observe(viewLifecycleOwner, Observer { applications ->
            applications?.let {
                meterRequestsAdapter.submitList(it)  // Update adapter with new data
            }
        })

        binding.edPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNumber = s.toString()
                viewModel.filterApplicationsByPhone(phoneNumber)
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
