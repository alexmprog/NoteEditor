package com.renovavision.noteeditor.presentation.ui.note

import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.common.mvp.MvpView

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

interface NoteListMvpView : MvpView {

    fun onNotesLoaded(notes: List<Note>)

    fun updateView()

    fun onSearchResult(notes: List<Note>)

    fun onAllNotesDeleted()

    fun onNoteDeleted()

    fun showInfoDialog(noteInfo: String)

    fun hideInfoDialog()

    fun showDeleteDialog(notePosition: Int)

    fun hideDeleteDialog()

    fun showContextDialog(notePosition: Int)

    fun hideContextDialog()

    fun showNoteScreen(noteId: Long)

}