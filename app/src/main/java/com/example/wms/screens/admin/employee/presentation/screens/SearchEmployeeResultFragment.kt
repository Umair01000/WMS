package com.example.wms.screens.admin.employee.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentSearchEmployeeResultBinding

class SearchEmployeeResultFragment : Fragment() {
    private var _binding: FragmentSearchEmployeeResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchEmployeeResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        val email = SearchEmployeeResultFragmentArgs.fromBundle(requireArguments()).email
        val password = SearchEmployeeResultFragmentArgs.fromBundle(requireArguments()).password
        binding.email.text = "Email: $email"
        binding.password.text = "Password: $password"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}