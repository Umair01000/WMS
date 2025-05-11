package com.example.wms.logins.presentation.employee

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wms.R
import com.example.wms.databinding.FragmentEmployeeLoginBinding
import com.example.wms.ui.employee.employee
import com.example.wms.utils.LoginResult
import com.example.wms.utils.PreferenceHelper

class EmployeeLoginFragment : Fragment() {
    private var _binding: FragmentEmployeeLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeLoginViewModel by viewModels()

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEmployeeLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoadingDialog()
        setupTextWatchers()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.Success -> {
                    dismissLoadingDialog()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Save role in SharedPreferences
                    PreferenceHelper.saveUserRole(requireContext(), "employee")

                    // Navigate to Employee Dashboard or Home
                    val intent = Intent(requireActivity(), employee::class.java)
                    startActivity(intent)
                }

                is LoginResult.Error -> {
                    dismissLoadingDialog()
                    showErrorDialog(result.message)
                }

                LoginResult.Loading -> {
                    showLoadingDialog()
                }
            }
        }
    }

    /**
     * Sets up the loading dialog.
     */
    private fun setupLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog?.setCancelable(false)
        loadingDialog?.setContentView(R.layout.dialog_loading)
    }

    /**
     * Displays the loading dialog.
     */
    private fun showLoadingDialog() {
        loadingDialog?.show()
    }

    /**
     * Dismisses the loading dialog.
     */
    private fun dismissLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    /**
     * Validates the input fields and returns true if valid.
     * Displays error messages if inputs are invalid.
     */
    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

        // Validate Email
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.login_error_empty_fields)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = getString(R.string.login_error_invalid_email)
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }

        // Validate Password
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.login_error_empty_fields)
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        return isValid
    }

    /**
     * Sets up TextWatchers for email and password fields to enable/disable the login button.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()
                binding.loginButton.isEnabled = isFormValid(email, password)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed during text changes
            }
        }

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
    }

    /**
     * Checks if the form is valid based on current input.
     * Returns true if email and password are non-empty and email has correct syntax.
     */
    private fun isFormValid(email: String, password: String): Boolean {
        return email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.isNotEmpty()
    }

    /**
     * Displays an error dialog with the provided message.
     */
    private fun showErrorDialog(message: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_error)

        val errorTextView = dialog.findViewById<TextView>(R.id.errorTextView)
        val closeButton = dialog.findViewById<Button>(R.id.closeButton)

        errorTextView.text = message

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoadingDialog()
        _binding = null
    }
}
