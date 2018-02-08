package com.renovavision.noteeditor.utils;

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

fun formatDate(date: Date?): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return dateFormat.format(date)
}
