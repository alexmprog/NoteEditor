package com.renovavision.noteeditor.presentation.ui.details;

import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.presentation.common.mvp.MvpView

/**
 * Created by Alexandr Golovach on 07.02.18.
 */

interface NoteDetailsMvpView : MvpView {

    fun showNote(note: Note)

    fun onSaved()

    fun onDeleted()

    fun showInfoDialog(noteInfo: String)

    fun hideInfoDialog()

    fun showDeleteDialog()

    fun hideDeleteDialog()
}
