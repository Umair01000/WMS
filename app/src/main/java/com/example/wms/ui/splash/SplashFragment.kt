package com.example.wms.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.R
import com.example.wms.databinding.FragmentSplashBinding
import com.example.wms.ui.employee.employee
import com.example.wms.ui.user.UserHomeActivity
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    private val splashDelay: Long = 2000 // 2 seconds

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Delay to simulate splash screen duration
        Handler(Looper.getMainLooper()).postDelayed({
            navigateBasedOnLoginState()
        }, splashDelay)
    }

    private fun navigateBasedOnLoginState() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser == null) {
            // User not logged in, navigate to UserLoginFragment
            findNavController().navigate(R.id.action_splashFragment_to_userLoginFragment)
        } else {
            // User is logged in, check role
            val userRole = sharedPreferences.getString("user_role", "user")

            when (userRole) {
                "admin" -> {
//                    findNavController().navigate(R.id.action_splashFragment_to_adminDashboardFragment)
                }

                "employee" -> {
                    // Navigate to User Dashboard or Home
                    val intent = Intent(requireActivity(), employee::class.java)
                    startActivity(intent)
                }

                "user" -> {
                    // Navigate to User Dashboard or Home
                    val intent = Intent(requireActivity(), UserHomeActivity::class.java)
                    startActivity(intent)
                }

                else -> {
                    // Navigate to User Dashboard or Home
                    val intent = Intent(requireActivity(), UserHomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
