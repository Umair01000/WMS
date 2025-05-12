package com.example.wms.screens.admin.timing.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentAdminTimerBinding
import com.example.wms.screens.admin.timing.presentation.AdminTimerViewModel
import com.example.wms.screens.admin.timing.domain.model.WorkingHours
import com.example.wms.screens.admin.timing.presentation.di.AdminTimerRequestModule

class AdminTimerFragment : Fragment() {

    private var _binding: FragmentAdminTimerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminTimerViewModel = AdminTimerRequestModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.workingHours.observe(viewLifecycleOwner, Observer { workingHours ->
            workingHours?.let {
                setTimePickers(it)
            }
        })
        viewModel.success.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                findNavController().navigate(AdminTimerFragmentDirections.actionAdminTimerFragmentToTimerSaveFragment())
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error.isNotEmpty()) {
               Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnNext.setOnClickListener {
            saveWorkingHours()
        }
    }

    private fun setTimePickers(workingHours: WorkingHours) {
        setTimePicker(binding.morningStartTimePicker, workingHours.morningStart)
        setTimePicker(binding.morningEndTimePicker, workingHours.morningEnd)

        setTimePicker(binding.afternoonStartTimePicker, workingHours.afternoonStart)
        setTimePicker(binding.afternoonEndTimePicker, workingHours.afternoonEnd)

        setTimePicker(binding.eveningStartTimePicker, workingHours.eveningStart)
        setTimePicker(binding.eveningEndTimePicker, workingHours.eveningEnd)
    }

    private fun setTimePicker(timePicker: TimePicker, time: String) {
        val timeParts = time.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].substring(0, 2).toInt()

        timePicker.hour = hour
        timePicker.minute = minute
    }

    private fun saveWorkingHours() {
        Log.d("AdminTimerFragment", "saveWorkingHours called")
        val morningStart = getTimeFromPicker(binding.morningStartTimePicker)
        val morningEnd = getTimeFromPicker(binding.morningEndTimePicker)
        val afternoonStart = getTimeFromPicker(binding.afternoonStartTimePicker)
        val afternoonEnd = getTimeFromPicker(binding.afternoonEndTimePicker)
        val eveningStart = getTimeFromPicker(binding.eveningStartTimePicker)
        val eveningEnd = getTimeFromPicker(binding.eveningEndTimePicker)
        val workingHours = WorkingHours(
            morningStart = morningStart,
            morningEnd = morningEnd,
            afternoonStart = afternoonStart,
            afternoonEnd = afternoonEnd,
            eveningStart = eveningStart,
            eveningEnd = eveningEnd
        )
        viewModel.saveWorkingHours(workingHours)
    }

    private fun getTimeFromPicker(timePicker: TimePicker): String {
        val hour = timePicker.hour
        val minute = timePicker.minute
        val amPm = if (hour < 12) "AM" else "PM"
        val hourIn12Format = if (hour > 12) hour - 12 else hour
        return String.format("%02d:%02d $amPm", hourIn12Format, minute)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}