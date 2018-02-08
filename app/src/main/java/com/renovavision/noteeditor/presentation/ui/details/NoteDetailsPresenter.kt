package com.renovavision.noteeditor.presentation.ui.details;

import com.renovavision.noteeditor.data.database.dao.NoteDao
import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.App
import com.renovavision.noteeditor.presentation.common.mvp.BasePresenter
import com.renovavision.noteeditor.presentation.ui.note.bus.NoteDeleteAction
import com.renovavision.noteeditor.presentation.ui.note.bus.NoteEditAction
import com.renovavision.noteeditor.utils.EventBus
import java.util.*
import javax.inject.Inject

/**
 * Created by Alexandr Golovach on 07.02.18.
 */

class NoteDetailsPresenter : BasePresenter<NoteDetailsMvpView>() {

    @Inject
    lateinit var noteDao: NoteDao

    @Inject
    lateinit var eventBus: EventBus

    lateinit private var note: Note;

    init {
        App.graph.inject(this)
    }

    fun loadData(noteId: Long) {
        val allNotes = noteDao.getAllNotes();
        note = noteDao.getNote(noteId)
        getMvpView()?.showNote(note)
    }

    fun saveNote(title: String, text: String) {
        note.title = title
        note.text = text
        note.changedDate = Date()
        noteDao.update(note)
        eventBus.send(NoteEditAction(note.id!!))
        getMvpView()?.onSaved()
    }

    fun deleteNote() {
        val noteId = note.id
        noteDao.delete(note)
        eventBus.send(NoteDeleteAction(noteId!!))
        getMvpView()?.onDeleted()
    }

    fun showNoteDeleteDialog() {
        getMvpView()?.showDeleteDialog()
    }

    fun hideNoteDeleteDialog() {
        getMvpView()?.hideDeleteDialog()
    }

    fun showNoteInfoDialog() {
        getMvpView()?.showInfoDialog(note.getInfo())
    }

    fun hideNoteInfoDialog() {
        getMvpView()?.hideInfoDialog()
    }
}
