package com.example.wms.logins.presentation.user.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wms.R
import com.example.wms.databinding.FragmentUserLoginBinding
import com.example.wms.utils.LoginResult
import com.example.wms.utils.PreferenceHelper

class UserLoginFragment : Fragment() {
    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserLoginViewModel by viewModels()

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoadingDialog()
        setupTextWatchers()

        // Set up Overflow Menu Button Click Listener
        binding.overflowMenuButton.setOnClickListener { view ->
            showOverflowMenu(view)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Proceed to login only if inputs are valid
            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        binding.signUpTextView.setOnClickListener {
            // Navigate to Sign Up Fragment
            findNavController().navigate(R.id.action_userLoginFragment_to_userSignupFragment)
        }

        binding.forgotPasswordTextView.setOnClickListener {
            // Navigate to Forgot Password Fragment
            findNavController().navigate(R.id.action_userLoginFragment_to_userForgetPasswordFragment)
        }

        // Observe login results
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
                    PreferenceHelper.saveUserRole(requireContext(), "user")

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
        }
    }

    /**
     * Displays the overflow menu with options to login as Admin or Employee.
     */
    private fun showOverflowMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.overflow_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem)
        }
        popupMenu.show()
    }

    /**
     * Handles overflow menu item clicks.
     */
    private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_login_admin -> {
                // Navigate to AdminLoginFragment
                findNavController().navigate(R.id.action_userLoginFragment_to_adminLoginFragment)
                true
            }

            R.id.menu_login_employee -> {
                // Navigate to EmployeeLoginFragment
                findNavController().navigate(R.id.action_userLoginFragment_to_employeeLoginFragment)
                true
            }

            else -> false
        }
    }

    private fun setupLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog?.setCancelable(false) // Prevent dialog from being dismissed by user
        loadingDialog?.setContentView(R.layout.dialog_loading)
    }

    private fun showLoadingDialog() {
        loadingDialog?.show()
    }

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
