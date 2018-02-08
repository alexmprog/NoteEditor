package com.renovavision.noteeditor.data.database.converter;

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

class DateConverter {

    @TypeConverter
    fun toDate(longDate: Long): Date {
        return Date(longDate)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time;
    }

}
