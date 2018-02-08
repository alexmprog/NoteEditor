package com.renovavision.noteeditor.presentation.ui.note

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.renovavision.noteeditor.R
import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.common.base.BaseMvpActivity
import com.renovavision.noteeditor.presentation.common.view.ItemClickSupport
import com.renovavision.noteeditor.presentation.ui.details.NoteDetailsActivity
import kotlinx.android.synthetic.main.activity_list_note.*

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

class NoteListActivity : BaseMvpActivity<NoteListMvpView, NoteListPresenter>(), NoteListMvpView {

    private var noteContextDialog: Dialog? = null
    private var noteDeleteDialog: Dialog? = null
    private var noteInfoDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ItemClickSupport.addTo(notesListView)) {
            setOnItemClickListener { _, position, _ -> mvpPresenter?.openNote(position) }
            setOnItemLongClickListener { _, position, _ -> mvpPresenter?.showContextDialog(position); true }
        }

        newNoteView.setOnClickListener { mvpPresenter?.openNewNote() }

        mvpPresenter?.attachView(this)
    }

    override fun onNotesLoaded(notes: List<Note>) {
        notesListView.adapter = NoteAdapter(notes)
        updateView()
    }

    override fun updateView() {
        notesListView.adapter.notifyDataSetChanged()
        if (notesListView.adapter.itemCount == 0) {
            notesListView.visibility = View.GONE
            notesListView.visibility = View.VISIBLE
        } else {
            notesListView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    override fun onSearchResult(notes: List<Note>) {
        notesListView.adapter = NoteAdapter(notes)
    }

    override fun onAllNotesDeleted() {
        updateView()
        Toast.makeText(this, R.string.all_notes_deleted, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteDeleted() {
        updateView()
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show()
    }

    override fun showInfoDialog(noteInfo: String) {
        noteInfoDialog = AlertDialog.Builder(this)
                .setTitle(R.string.note_info)
                .setPositiveButton(getString(R.string.ok), { dialog, which ->
                    mvpPresenter?.hideNoteInfoDialog()
                })
                .setMessage(noteInfo)
                .setOnCancelListener { mvpPresenter?.hideNoteInfoDialog() }
                .show()
    }

    override fun hideInfoDialog() {
        noteInfoDialog?.dismiss()
    }

    override fun showDeleteDialog(notePosition: Int) {
        noteDeleteDialog = AlertDialog.Builder(this)
                .setTitle(R.string.note_deletion_title)
                .setPositiveButton(getString(R.string.ok), { dialog, which ->
                    mvpPresenter?.hideNoteInfoDialog()
                    mvpPresenter?.deleteNoteByPosition(notePosition)
                })
                .setMessage(R.string.note_deletion_message)
                .setOnCancelListener { mvpPresenter?.hideNoteInfoDialog() }
                .show()
    }

    override fun hideDeleteDialog() {
        noteDeleteDialog?.dismiss()
    }

    override fun showContextDialog(notePosition: Int) {
        noteContextDialog = AlertDialog.Builder(this)
                .setItems(R.array.main_note_context, { dialog, which ->
                    onContextDialogItemClick(which, notePosition)
                    mvpPresenter?.hideContextDialog()
                })
                .setOnCancelListener { mvpPresenter?.hideContextDialog() }
                .show()
    }

    override fun hideContextDialog() {
        noteContextDialog?.dismiss()
    }

    override val layoutRes: Int
        get() = R.layout.activity_list_note

    override fun showNoteScreen(noteId: Long) {
        val intent = NoteDetailsActivity.buildIntent(this, noteId)
        startActivity(intent)
    }

    override fun createMvpPresenter(): NoteListPresenter {
        return NoteListPresenter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_list, menu)

        initSearchView(menu)
        return true
    }

    private fun initSearchView(menu: Menu) {
        val searchViewMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                mvpPresenter?.search(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true;
            }
        })
        searchView.setOnCloseListener { mvpPresenter?.search(""); false }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDeleteAllNotes -> mvpPresenter?.deleteAllNotes()
            R.id.menuSortByName -> mvpPresenter?.sortNotesBy(NoteListPresenter.SortNotesBy.NAME)
            R.id.menuSortByDate -> mvpPresenter?.sortNotesBy(NoteListPresenter.SortNotesBy.DATE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onContextDialogItemClick(contextItemPosition: Int, notePosition: Int) {
        when (contextItemPosition) {
            0 -> mvpPresenter?.openNote(notePosition)
            1 -> mvpPresenter?.showNoteInfo(notePosition)
            2 -> mvpPresenter?.showDeleteDialog(notePosition)
        }
    }


}
