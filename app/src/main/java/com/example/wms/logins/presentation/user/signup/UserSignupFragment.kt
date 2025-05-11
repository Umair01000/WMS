package com.example.wms.logins.presentation.user.signup

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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wms.R
import com.example.wms.databinding.FragmentUserSignupBinding
import com.example.wms.logins.presentation.user.login.UserLoginFragmentDirections
import com.example.wms.ui.user.UserHomeActivity
import com.example.wms.utils.LoginResult
import com.example.wms.utils.PreferenceHelper

class UserSignupFragment : Fragment() {

    private var _binding: FragmentUserSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserSignupViewModel by viewModels()

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoadingDialog()
        setupTextWatchers()

        // Back Button Click Listener
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Signup Button Click Listener
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (validateInputs(email, password, confirmPassword)) {
                viewModel.signup(email, password)
            }
        }

        // Observe Signup Results
        viewModel.signupResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is LoginResult.Success -> {
                    dismissLoadingDialog()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.signup_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Save role in SharedPreferences
                    PreferenceHelper.saveUserRole(requireContext(), "user")

                    // Navigate to User Dashboard or Login Screen
                    // Navigate to User Dashboard or Home
                    findNavController().navigate(UserLoginFragmentDirections.actionUserLoginFragmentToUserHomeFragment())
                }

                is LoginResult.Error -> {
                    dismissLoadingDialog()
                    showErrorDialog(result.message)
                }

                LoginResult.Loading -> {
                    showLoadingDialog()
                }
            }
        })
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
     * Validates the input fields.
     * Returns true if all validations pass.
     */
    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        // Validate Email
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.signup_error_empty_fields)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = getString(R.string.signup_error_invalid_email)
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }

        // Validate Password
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.signup_error_empty_fields)
            isValid = false
        } else if (password.length < 8) {
            binding.passwordInputLayout.error = getString(R.string.signup_error_password_length)
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        // Validate Confirm Password
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputLayout.error = getString(R.string.signup_error_empty_fields)
            isValid = false
        } else if (confirmPassword != password) {
            binding.confirmPasswordInputLayout.error =
                getString(R.string.signup_error_password_mismatch)
            isValid = false
        } else {
            binding.confirmPasswordInputLayout.error = null
        }

        return isValid
    }

    /**
     * Sets up TextWatchers for real-time validation and enabling/disabling the signup button.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()
                val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
                binding.signupButton.isEnabled = isFormValid(email, password, confirmPassword)
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
        binding.confirmPasswordEditText.addTextChangedListener(textWatcher)
    }

    /**
     * Checks if the form is valid based on current input.
     * Returns true if email and password are valid and passwords match.
     */
    private fun isFormValid(email: String, password: String, confirmPassword: String): Boolean {
        return email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.isNotEmpty() &&
                password.length >= 8 &&
                confirmPassword.isNotEmpty() &&
                password == confirmPassword
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
