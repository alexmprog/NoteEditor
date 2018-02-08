package com.renovavision.noteeditor.data.database.dao

import android.arch.persistence.room.*
import com.renovavision.noteeditor.data.model.Note

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): MutableList<Note>

    @Insert
    fun insert(note: Note) : Long

    @Update
    fun update(vararg authors: Note)

    @Delete
    fun delete(vararg authors: Note)

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT * FROM note WHERE id=:id")
    fun getNote(id: Long): Note

}