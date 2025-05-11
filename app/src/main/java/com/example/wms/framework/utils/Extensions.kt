package com.example.wms.framework.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView

/**
 * Sets text as “[label][value]” with the label in **bold** and the value normal.
 */
fun TextView.setLabelValue(label: String, value: String) {
    val ssb = SpannableStringBuilder()
    ssb.append(label).setSpan(
        StyleSpan(Typeface.BOLD),/* start = */
        0,/* end   = */
        label.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    ssb.append(value)
    text = ssb
}