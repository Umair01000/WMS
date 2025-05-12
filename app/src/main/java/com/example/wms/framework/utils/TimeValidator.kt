package com.example.wms.framework.utils

import java.text.SimpleDateFormat
import java.util.Locale

object TimeValidator {
    fun isValidTimeRange(startTime: String, endTime: String): Boolean {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val startDate = timeFormat.parse(startTime)
        val endDate = timeFormat.parse(endTime)

        return startDate != null && endDate != null && !endDate.before(startDate)
    }
}
