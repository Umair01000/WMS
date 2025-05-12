package com.example.wms.screens.user.chatbot.presentation

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wms.databinding.FragmentChatbotBinding
import com.example.wms.screens.user.chatbot.domain.model.ContentRequest
import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.PartRequest
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest
import com.example.wms.screens.user.chatbot.presentation.di.ChatbotModule

class ChatbotFragment : Fragment() {

    private val geminiViewModel = ChatbotModule.provideViewModel()
    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler()

    private val loadingMessages = listOf(
        "AI is checking...",
        "Getting response...",
        "Composing best response...",
        "Checking response...",
        "Finishing..."
    )
    private var currentMessageIndex = 0
    private var loadingAnimationStartedAt: Long = 0
    private val minimumLoadingTime = 5000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.submitButton.isEnabled = false
        binding.queryTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?, start: Int, before: Int, count: Int
            ) {
                binding.submitButton.isEnabled = charSequence?.length ?: 0 > 8
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        binding.submitButton.setOnClickListener {
            onSubmitClicked()
        }

        geminiViewModel.geminiResponse.observe(viewLifecycleOwner) { response ->
            handleGeminiResponse(response)
        }
    }

    private fun onSubmitClicked() {
        val queryText = binding.queryTextField.text.toString()
        val contentRequest = ContentRequest(parts = listOf(PartRequest(text = queryText)))
        val queryRequest = QueryRequest(contents = listOf(contentRequest))

        binding.headingTitle.visibility = View.GONE
        binding.queryTextField.visibility = View.GONE
        binding.submitButton.visibility = View.GONE
        binding.loadingBoard.visibility = View.VISIBLE
        loadingAnimationStartedAt = System.currentTimeMillis()
        startLoadingAnimation()

        geminiViewModel.getGeminiResponse(queryRequest)
    }

    private fun handleGeminiResponse(response: GeminiResponse) {
        val timeTaken = System.currentTimeMillis() - loadingAnimationStartedAt
        val remainingTime = minimumLoadingTime - timeTaken
        if (remainingTime > 0) {
            handler.postDelayed({
                showResponse(response)
            }, remainingTime)
        } else {
            showResponse(response)
        }
    }

    private fun showResponse(response: GeminiResponse) {
        binding.loadingBoard.visibility = View.GONE
        if (response.candidates.isNotEmpty()) {
            val text = response.candidates[0].content.parts.firstOrNull()?.text
            binding.responseText.text = ""
            animateTextWordByWord(text ?: "No response available.")
        } else {
            binding.responseText.text = "Sorry, no response available."
            binding.resultBoard.visibility = View.VISIBLE
            binding.responseText.visibility = View.VISIBLE
        }
    }

    private fun animateTextWordByWord(text: String) {
        val words = text.split(" ")
        var currentWordIndex = 0
        val typingSpeed = 150L

        binding.responseText.text = ""
        val typeWordRunnable = object : Runnable {
            override fun run() {
                if (currentWordIndex < words.size) {
                    binding.responseText.append("${words[currentWordIndex]} ")
                    currentWordIndex++
                    handler.postDelayed(this, typingSpeed)
                }
            }
        }

        handler.post(typeWordRunnable)
        binding.resultBoard.visibility = View.VISIBLE
        binding.responseText.visibility = View.VISIBLE
    }

    private fun startLoadingAnimation() {
        handler.postDelayed({
            if (currentMessageIndex < loadingMessages.size) {
                binding.loadingText.text = loadingMessages[currentMessageIndex]
                currentMessageIndex++
                startLoadingAnimation()
            } else {
                currentMessageIndex = 0
            }
        }, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)
    }
}