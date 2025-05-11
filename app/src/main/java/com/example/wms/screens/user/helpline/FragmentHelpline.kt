package com.example.wms.screens.user.helpline

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentHelplineBinding

class FragmentHelpline : Fragment() {
    private var _binding: FragmentHelplineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelplineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCall.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:1334".toUri()
            }
            startActivity(dialIntent)
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}