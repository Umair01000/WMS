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
import com.example.wms.databinding.FragmentAddEmployeePasswordBinding
import com.example.wms.screens.admin.employee.domain.result.EmployeeResult
import com.example.wms.screens.admin.employee.presentation.AddEmployeeViewModel
import com.example.wms.screens.admin.employee.presentation.di.AddEmployeeModule

class AddEmployeePasswordFragment : Fragment() {

    private var _binding: FragmentAddEmployeePasswordBinding? = null
    private val binding get() = _binding!!
    private var viewModel: AddEmployeeViewModel = AddEmployeeModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEmployeePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = arguments?.getString("email") ?: ""

        binding.btnAddEmployee.isEnabled = false
        binding.edPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                binding.btnAddEmployee.isEnabled = password.length >= 6
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnAddEmployee.setOnClickListener {
            val password = binding.edPassword.text.toString()
            binding.progressBar.visibility = View.VISIBLE
            viewModel.addEmployee(email, password)

            viewModel.employeeResult.observe(viewLifecycleOwner, Observer { result ->
                binding.progressBar.visibility = View.GONE

                when (result) {
                    is EmployeeResult.Success -> {
                        findNavController().navigate(AddEmployeePasswordFragmentDirections.actionAddEmployeePasswordFragmentToAddEmployeeSuccessFragment())
                    }

                    is EmployeeResult.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }

                    EmployeeResult.Loading -> Unit
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}