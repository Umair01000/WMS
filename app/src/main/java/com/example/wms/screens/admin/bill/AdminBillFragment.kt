package com.example.wms.screens.admin.bill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentAdminBillBinding
import kotlin.text.all

class AdminBillFragment : Fragment() {
    private var _binding: FragmentAdminBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            edConsumerNumber.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    btnNext.isEnabled = s?.length == 8 && s.all { it.isDigit() }
                }

                override fun beforeTextChanged(a: CharSequence?, i: Int, j: Int, k: Int) = Unit
                override fun onTextChanged(a: CharSequence?, i: Int, j: Int, k: Int) = Unit
            })
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            btnNext.setOnClickListener {
                val consumerNumber = edConsumerNumber.text.toString()
                findNavController().navigate(
                    AdminBillFragmentDirections.actionAdminBillFragmentToAdminBillDetailsFragment(
                        consumerNumber
                    )
                )
            }
        }

    }
}