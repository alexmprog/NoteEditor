package com.renovavision.noteeditor.presentation.ui.note

import com.renovavision.noteeditor.data.database.dao.NoteDao
import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.App
import com.renovavision.noteeditor.presentation.common.mvp.BasePresenter
import com.renovavision.noteeditor.presentation.ui.note.bus.NoteDeleteAction
import com.renovavision.noteeditor.presentation.ui.note.bus.NoteEditAction
import com.renovavision.noteeditor.utils.EventBus
import com.renovavision.noteeditor.utils.getNotesSortMethodName
import com.renovavision.noteeditor.utils.setNotesSortMethod
import java.util.*
import javax.inject.Inject

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

class NoteListPresenter : BasePresenter<NoteListMvpView>() {

    enum class SortNotesBy : Comparator<Note> {
        DATE {
            override fun compare(note1: Note, note2: Note) = note1.changedDate!!.compareTo(note2.changedDate)
        },
        NAME {
            override fun compare(note1: Note, note2: Note) = note1.title!!.compareTo(note2.title!!)
        },
    }

    @Inject
    lateinit var noteDao: NoteDao

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var notesList: MutableList<Note>


    init {
        App.graph.inject(this)
    }

    override fun attachView(mvpView: NoteListMvpView) {
        super.attachView(mvpView)
        loadAllNotes()

        eventBus.observe<NoteEditAction>()
                .subscribe({
                    val notePosition = getNotePositionById(it.noteId)
                    notesList[notePosition] = noteDao.getNote(it.noteId)
                    sortNotesBy(getCurrentSortMethod())
                });

        eventBus.observe<NoteDeleteAction>()
                .subscribe({
                    val notePosition = getNotePositionById(it.noteId)
                    notesList.removeAt(notePosition)
                    getMvpView()?.updateView()
                });
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
        notesList.removeAll(notesList)
        getMvpView()?.updateView()
    }

    fun deleteNoteByPosition(position: Int) {
        if (isViewAttached) {
            val note = notesList[position]
            noteDao.delete(note)
            notesList.remove(note)
            getMvpView()?.onNoteDeleted()
        }
    }

    fun openNewNote() {
        if (isViewAttached) {
            val newNote = Note();
            newNote.changedDate = Date();
            newNote.createdDate = Date()
            val id = noteDao.insert(newNote)
            newNote.id = id
            notesList.add(newNote)
            sortNotesBy(getCurrentSortMethod())
            getMvpView()?.showNoteScreen(newNote.id!!)
        }
    }

    fun openNote(position: Int) {
        if (isViewAttached) {
            getMvpView()?.showNoteScreen(notesList[position].id!!)
        }
    }

    fun search(query: String) {
        if (isViewAttached) {
            if (query == "") {
                getMvpView()?.onSearchResult(notesList)
            } else {
                val searchResults = notesList.filter { it.title!!.startsWith(query, ignoreCase = true) }
                getMvpView()?.onSearchResult(searchResults)
            }
        }
    }

    fun sortNotesBy(sortMethod: SortNotesBy) {
        if (isViewAttached) {
            notesList.sortWith(sortMethod)
            setNotesSortMethod(sortMethod.toString())
            getMvpView()?.updateView()
        }
    }

    fun showContextDialog(position: Int) {
        if (isViewAttached) {
            getMvpView()?.showContextDialog(position)
        }
    }

    fun hideContextDialog() {
        if (isViewAttached) {
            getMvpView()?.hideContextDialog()
        }
    }

    fun showDeleteDialog(position: Int) {
        if (isViewAttached) {
            getMvpView()?.showDeleteDialog(position)
        }
    }

    fun hideDeleteDialog() {
        if (isViewAttached) {
            getMvpView()?.hideDeleteDialog()
        }
    }

    fun showNoteInfo(position: Int) {
        if (isViewAttached) {
            getMvpView()?.showInfoDialog(notesList[position].getInfo())
        }
    }

    fun hideNoteInfoDialog() {
        if (isViewAttached) {
            getMvpView()?.hideInfoDialog()
        }
    }

    private fun loadAllNotes() {
        if (isViewAttached) {
            notesList = noteDao.getAllNotes()
            Collections.sort(notesList, getCurrentSortMethod())
            getMvpView()?.onNotesLoaded(notesList)
        }
    }

    private fun getCurrentSortMethod(): SortNotesBy {
        val defaultSortMethodName = SortNotesBy.DATE.toString()
        val currentSortMethodName = getNotesSortMethodName(defaultSortMethodName)
        return SortNotesBy.valueOf(currentSortMethodName)
    }

    private fun getNotePositionById(noteId: Long) = notesList.indexOfFirst { it.id == noteId }

}