package com.example.wms.screens.user.bill.data.repository

import com.example.wms.screens.user.bill.data.remote.BillApiService
import com.example.wms.screens.user.bill.domain.model.BillDetails
import com.example.wms.screens.user.bill.domain.repository.BillDetailsRepository
import com.example.wms.screens.user.bill.domain.repository.BillResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

private const val PLACEHOLDER = "Not issued yet"
private fun Element.cell(idx: Int): String =
    this.select("td")
        .getOrNull(idx)
        ?.text()
        ?.trim()
        .takeIf { it?.isNotEmpty() == true }
        ?: PLACEHOLDER

private fun List<Element>.cell(idx: Int): String =
    this.getOrNull(idx)
        ?.text()
        ?.trim()
        .takeIf { it?.isNotEmpty() == true }
        ?: PLACEHOLDER

class BillDetailsRepositoryImpl(
    private val api: BillApiService
) : BillDetailsRepository {

    override suspend fun fetchBill(cCode: String): BillResult = withContext(Dispatchers.IO) {
        try {
            val resp = api.getBillHtml(cCode)
            if (!resp.isSuccessful) {
                return@withContext BillResult.Error("HTTP ${resp.code()}")
            }
            val html = resp.body()!!.string()
            val bill = parseBillHtml(html)
            BillResult.Success(bill, html)
        } catch (e: Exception) {
            BillResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    private fun parseBillHtml(html: String): BillDetails {
        val doc = Jsoup.parse(html)

        val table1 = doc
            .selectFirst("div#table1 > table")
            ?: throw IllegalStateException("Couldn’t find Connection Details table")
        val dataRows = table1.select("tr")
            .drop(1)
            .filter { it.select("td").size >= 6 }

        val row1 = dataRows.getOrNull(0)
            ?: throw IllegalStateException("Missing row 1 in table1")
        val cCodeText      = row1.cell(1)
        val category       = row1.cell(3)
        val connectionDate = row1.cell(5)

        val row2 = dataRows.getOrNull(1)
            ?: throw IllegalStateException("Missing row 2 in table1")
        val wasaNo = row2.cell(1)
        val type   = row2.cell(3)
        val units  = row2.cell(5)

        val row3 = dataRows.getOrNull(2)
            ?: throw IllegalStateException("Missing row 3 in table1")
        val wardNo      = row3.cell(1)
        val uc          = row3.cell(3)
        val ferruleSize = row3.cell(5)

        val detail  = dataRows.getOrNull(3)?.cell(1) ?: PLACEHOLDER
        val name    = dataRows.getOrNull(4)?.cell(1) ?: PLACEHOLDER
        val address = dataRows.getOrNull(5)?.cell(1) ?: PLACEHOLDER

        val history = table1.select("tr")
            .drop(8)
            .takeWhile { it.select("th").isEmpty() }
            .map { tr ->
                val cols = tr.select("td")
                BillDetails.BillingHistoryEntry(
                    billingPeriod = cols.cell(0),
                    amountPaid    = cols.cell(1)
                )
            }

        val allRows1 = table1.select("tr")
        val meterDetails = BillDetails.MeterDetails(
            meterType           = allRows1.getOrNull(7)?.select("td")?.cell(0) ?: PLACEHOLDER,
            meterStatus         = allRows1.getOrNull(8)?.select("td")?.cell(2) ?: PLACEHOLDER,
            lastMeterReading    = allRows1.getOrNull(9)?.select("td")?.cell(2) ?: PLACEHOLDER,
            currentMeterReading = allRows1.getOrNull(10)?.select("td")?.cell(2) ?: PLACEHOLDER,
            totalUnitsConsumed  = allRows1.getOrNull(11)?.select("td")?.cell(2) ?: PLACEHOLDER
        )

        val table2 = doc
            .selectFirst("div#table2 > table")
            ?: throw IllegalStateException("Couldn’t find Billing Details table")
        val t2 = table2.select("tr")

        val billingDetails = BillDetails.BillingDetailsSection(
            billingPeriod              = t2.getOrNull(1)?.select("td")?.cell(1) ?: PLACEHOLDER,
            issueDate                  = t2.getOrNull(2)?.select("td")?.cell(1) ?: PLACEHOLDER,
            dueDate                    = t2.getOrNull(3)?.select("td")?.cell(1) ?: PLACEHOLDER,
            waterBill                  = t2.getOrNull(5)?.select("td")?.cell(1) ?: PLACEHOLDER,
            aquiferCharges             = t2.getOrNull(6)?.select("td")?.cell(1) ?: PLACEHOLDER,
            sewerageBill               = t2.getOrNull(7)?.select("td")?.cell(1) ?: PLACEHOLDER,
            fixedChargesMisc           = t2.getOrNull(8)?.select("td")?.cell(1) ?: PLACEHOLDER,
            currentAmount              = t2.getOrNull(9)?.select("td")?.cell(1) ?: PLACEHOLDER,
            arrears                    = t2.getOrNull(10)?.select("td")?.cell(1) ?: PLACEHOLDER,
            adjustment                 = t2.getOrNull(11)?.select("td")?.cell(1) ?: PLACEHOLDER,
            discount                   = t2.getOrNull(12)?.select("td")?.cell(1) ?: PLACEHOLDER,
            amountPayableWithinDueDate = t2.getOrNull(13)?.select("td")?.cell(1) ?: PLACEHOLDER,
            surcharge                  = t2.getOrNull(14)?.select("td")?.cell(1) ?: PLACEHOLDER,
            amountPayableAfterDueDate  = t2.getOrNull(15)?.select("td")?.cell(1) ?: PLACEHOLDER
        )

        return BillDetails(
            cCode = cCodeText,
            category = category,
            connectionDate = connectionDate,
            wasaNo = wasaNo,
            type = type,
            units = units,
            wardNo = wardNo,
            uc = uc,
            ferruleSize = ferruleSize,
            detail = detail,
            name = name,
            address = address,
            billingHistory = history,
            meterDetails = meterDetails,
            billingDetails = billingDetails
        )
    }
}
