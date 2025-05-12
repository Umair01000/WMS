package com.example.wms.screens.admin.bill

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wms.databinding.FragmentAdminBillDetailsBinding
import com.example.wms.framework.utils.setLabelValue
import com.example.wms.screens.user.bill.di.BillingModule
import com.example.wms.screens.user.bill.presentation.BillDetailsFragmentArgs
import com.example.wms.screens.user.bill.presentation.BillDetailsViewModel
import com.example.wms.screens.user.bill.presentation.state.BillDetailsState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.getValue

class AdminBillDetailsFragment : Fragment() {
    private var _binding: FragmentAdminBillDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: AdminBillDetailsFragmentArgs by navArgs()
    private val viewModel: BillDetailsViewModel = BillingModule.provideViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentAdminBillDetailsBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BillDetailsState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.detailsContainer.visibility = View.GONE
                    binding.btnDownload.visibility = View.GONE
                }

                is BillDetailsState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.detailsContainer.visibility = View.VISIBLE
                    binding.btnDownload.visibility = View.VISIBLE
                    binding.tvConsumerId.setLabelValue("Consumer ID: ", state.bill.cCode)
                    binding.tvBillingPeriod.setLabelValue(
                        "Billing Period: ", state.bill.billingDetails.billingPeriod
                    )
                    binding.tvIssueDate.setLabelValue(
                        "Issue Date: ", state.bill.billingDetails.issueDate
                    )
                    binding.tvDueDate.setLabelValue("Due Date: ", state.bill.billingDetails.dueDate)
                    binding.tvAmountPayable.setLabelValue(
                        "Amount Payable: ", state.bill.billingDetails.amountPayableWithinDueDate
                    )

                    binding.btnDownload.setOnClickListener {
                        renderPdfFromHtml(state.html, args.cCode)
                    }
                }

                is BillDetailsState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }

        viewModel.loadBill(args.cCode)
    }

    private fun renderPdfFromHtml(html: String, cCode: String) {
        val baseUrl = "https://www.wasarwp.gop.pk/new.php?C_Code=$cCode"
        binding.webView.apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = false

            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView, url: String) {
                    view.postDelayed({ captureWebViewToPdf(view, cCode) }, 250)
                }
            }

            loadDataWithBaseURL(baseUrl, html, "text/html", "UTF-8", null)
        }
    }

    private fun captureWebViewToPdf(webView: WebView, cCode: String) {
        val width = webView.width.takeIf { it > 0 } ?: resources.displayMetrics.widthPixels
        val height =
            ((webView.contentHeight.coerceAtLeast(1)) * resources.displayMetrics.density).toInt()
                .coerceAtLeast(1)
        webView.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        webView.layout(0, 0, width, height)

        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
        val page = document.startPage(pageInfo)
        webView.draw(page.canvas)
        document.finishPage(page)

        val downloads = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
        val pdfFile = File(downloads, "bill_${cCode}.pdf")

        try {
            FileOutputStream(pdfFile).use { out -> document.writeTo(out) }
            Toast.makeText(
                requireContext(), "PDF saved to Downloads/${pdfFile.name}", Toast.LENGTH_LONG
            ).show()
        } catch (e: IOException) {
            Toast.makeText(
                requireContext(), "Error saving PDF: ${e.localizedMessage}", Toast.LENGTH_LONG
            ).show()
        } finally {
            document.close()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}