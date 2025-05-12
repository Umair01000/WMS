package com.example.wms.screens.user.watertiming.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.wms.databinding.FragmentUserWorkingHoursBinding
import com.example.wms.screens.user.workinghours.presentation.di.UserWorkingHoursModule

class UserWorkingHoursFragment : Fragment() {

    private var _binding: FragmentUserWorkingHoursBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserWorkingHoursViewModel = UserWorkingHoursModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserWorkingHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the user working hours from the ViewModel
        viewModel.userWorkingHours.observe(viewLifecycleOwner, Observer { workingHours ->
            // Set the text of TextViews to display the fetched working hours
            workingHours?.let {
                binding.morningStartTextView.text = it.morningStart
                binding.morningEndTextView.text = it.morningEnd

                binding.afternoonStartTextView.text = it.afternoonStart
                binding.afternoonEndTextView.text = it.afternoonEnd

                binding.eveningStartTextView.text = it.eveningStart
                binding.eveningEndTextView.text = it.eveningEnd
            }
        })

        // Observe error events
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}