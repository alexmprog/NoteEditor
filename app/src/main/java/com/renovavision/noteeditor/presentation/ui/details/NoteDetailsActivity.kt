package com.renovavision.noteeditor.presentation.ui.details

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.renovavision.noteeditor.R
import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.common.base.BaseMvpActivity
import com.renovavision.noteeditor.utils.formatDate
import kotlinx.android.synthetic.main.activity_note_details.*

/**
 * Created by Alexandr Golovach on 07.02.18.
 */

class NoteDetailsActivity : BaseMvpActivity<NoteDetailsMvpView, NoteDetailsPresenter>(), NoteDetailsMvpView {

    companion object {

        const val NOTE_DELETE_ARG = "note_id"

        fun buildIntent(activity: Activity, noteId: Long): Intent {
            val intent = Intent(activity, NoteDetailsActivity::class.java)
            intent.putExtra(NOTE_DELETE_ARG, noteId)
            return intent
        }
    }

    private var noteDeleteDialog: AlertDialog? = null
    private var noteInfoDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleView.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val editText = view as EditText
                editText.setSelection((editText.text.length))
            }
        }

        mvpPresenter?.attachView(this)

        val noteId = intent.extras.getLong(NOTE_DELETE_ARG, -1)
        mvpPresenter?.loadData(noteId)
    }

    override fun showNote(note: Note) {
        dateView.text = formatDate(note.changedDate)
        titleView.setText(note.title)
        contentView.setText(note.text)
    }

    override fun onSaved() {
        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDeleted() {
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_details, menu)
        return true
    }

    override fun showInfoDialog(noteInfo: String) {
        noteInfoDialog = AlertDialog.Builder(this)
                .setTitle(R.string.note_info)
                .setPositiveButton(R.string.ok, { dialog, which ->
                    mvpPresenter?.hideNoteInfoDialog()
                })
                .setMessage(noteInfo)
                .setOnCancelListener() { mvpPresenter?.hideNoteInfoDialog() }
                .show()
    }

    override fun hideInfoDialog() {
        noteInfoDialog?.dismiss()
    }

    override fun showDeleteDialog() {
        noteDeleteDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.note_deletion_title))
                .setMessage(getString(R.string.note_deletion_message))
                .setPositiveButton(getString(R.string.yes),
                        { dialog, which ->
                            mvpPresenter?.hideNoteInfoDialog()
                            mvpPresenter?.deleteNote()
                        })
                .setNegativeButton(getString(R.string.no), { dialog, which ->
                    mvpPresenter?.hideNoteInfoDialog()
                })
                .setOnCancelListener() { mvpPresenter?.hideNoteInfoDialog() }
                .show()
    }

    override fun hideDeleteDialog() {
        noteDeleteDialog?.dismiss()
    }

    override val layoutRes: Int
        get() = R.layout.activity_note_details;

    override fun createMvpPresenter(): NoteDetailsPresenter {
        return NoteDetailsPresenter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSaveNote -> mvpPresenter?.saveNote(titleView.text.toString(), contentView.text.toString())

            R.id.menuDeleteNote -> mvpPresenter?.showNoteDeleteDialog()

            R.id.menuNoteInfo -> mvpPresenter?.showNoteInfoDialog()
        }
        return super.onOptionsItemSelected(item)
    }


}