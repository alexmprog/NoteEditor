package com.renovavision.noteeditor.utils

import android.content.Context
import com.renovavision.noteeditor.presentation.App

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

private val prefs by lazy {
    App.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
}

fun getNotesSortMethodName(defaultValue: String): String = prefs.getString("sort_method", defaultValue)

fun setNotesSortMethod(sortMethod: String) {
    prefs.edit().putString("sort_method", sortMethod).apply()
}