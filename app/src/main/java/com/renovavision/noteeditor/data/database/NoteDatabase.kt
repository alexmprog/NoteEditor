package com.renovavision.noteeditor.data.database

import android.arch.persistence.room.*
import com.renovavision.noteeditor.data.database.converter.DateConverter
import com.renovavision.noteeditor.data.database.dao.NoteDao
import com.renovavision.noteeditor.data.model.Note

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Database(entities = arrayOf(Note::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}