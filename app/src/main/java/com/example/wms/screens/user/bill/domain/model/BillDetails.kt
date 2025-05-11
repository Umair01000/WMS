package com.example.wms.screens.user.bill.domain.model

data class BillDetails(
    val cCode: String,
    val category: String,
    val connectionDate: String,
    val wasaNo: String,
    val type: String,
    val units: String,
    val wardNo: String,
    val uc: String,
    val ferruleSize: String,
    val detail: String,
    val name: String,
    val address: String,
    val billingHistory: List<BillingHistoryEntry>,
    val meterDetails: MeterDetails,
    val billingDetails: BillingDetailsSection
) {
    data class BillingHistoryEntry(
        val billingPeriod: String,
        val amountPaid: String
    )
    data class MeterDetails(
        val meterType: String,
        val meterStatus: String,
        val lastMeterReading: String,
        val currentMeterReading: String,
        val totalUnitsConsumed: String
    )
    data class BillingDetailsSection(
        val billingPeriod: String,
        val issueDate: String,
        val dueDate: String,
        val waterBill: String,
        val aquiferCharges: String,
        val sewerageBill: String,
        val fixedChargesMisc: String,
        val currentAmount: String,
        val arrears: String,
        val adjustment: String,
        val discount: String,
        val amountPayableWithinDueDate: String,
        val surcharge: String,
        val amountPayableAfterDueDate: String
    )
}
