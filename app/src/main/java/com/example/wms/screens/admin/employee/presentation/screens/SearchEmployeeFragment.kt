package com.example.wms.screens.admin.employee.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentSearchEmployeeBinding
import com.example.wms.screens.admin.employee.presentation.AddEmployeeViewModel
import com.example.wms.screens.admin.employee.presentation.di.AddEmployeeModule

class SearchEmployeeFragment : Fragment() {

    private var _binding: FragmentSearchEmployeeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: AddEmployeeViewModel = AddEmployeeModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnContinue.isEnabled = false
        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.emailValid.observe(viewLifecycleOwner, Observer { isValid ->
            binding.btnContinue.isEnabled = isValid
        })

        binding.btnContinue.setOnClickListener {
            val email = binding.edEmail.text.toString()
            binding.progressBar.visibility = View.VISIBLE
            val employee = viewModel.getEmployeeByEmail(email)
            if (employee != null) {
                val action = SearchEmployeeFragmentDirections
                    .actionSearchEmployeeFragmentToSearchEmployeeResultFragment(
                        employee.email,
                        employee.password
                    )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "No employee found", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}