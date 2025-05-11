package com.example.wms.logins.presentation.user.forget

import android.app.Dialog
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
import com.example.wms.databinding.FragmentUserForgetPasswordBinding
import com.example.wms.utils.LoginResult

class UserForgetPasswordFragment : Fragment() {

    private var _binding: FragmentUserForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserForgetPasswordViewModel by viewModels()

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoadingDialog()
        setupTextWatchers()

        // Back Button Click Listener
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Reset Password Button Click Listener
        binding.resetPasswordButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()

            if (validateEmail(email)) {
                viewModel.resetPassword(email)
            }
        }

        // Observe Reset Password Results
        viewModel.resetPasswordResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is LoginResult.Success -> {
                    dismissLoadingDialog()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.reset_password_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate back to Login Screen or Dashboard
                    findNavController().navigateUp()
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
     * Validates the email input.
     * Returns true if email is valid.
     */
    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.emailInputLayout.error =
                    getString(R.string.reset_password_error_empty_email)
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInputLayout.error =
                    getString(R.string.reset_password_error_invalid_email)
                false
            }

            else -> {
                binding.emailInputLayout.error = null
                true
            }
        }
    }

    /**
     * Sets up TextWatchers for real-time validation and enabling/disabling the reset password button.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailEditText.text.toString().trim()
                binding.resetPasswordButton.isEnabled = isEmailValid(email)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed during text changes
            }
        }

        binding.emailEditText.addTextChangedListener(textWatcher)
    }

    /**
     * Checks if the email is valid.
     */
    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
