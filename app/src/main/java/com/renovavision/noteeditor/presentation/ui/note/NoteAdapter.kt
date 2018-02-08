package com.renovavision.noteeditor.presentation.ui.note;

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renovavision.noteeditor.R
import com.renovavision.noteeditor.data.model.Note
import com.renovavision.noteeditor.utils.formatDate
import kotlinx.android.synthetic.main.note_item_layout.view.*

class NoteAdapter(private val notesList: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NoteAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.note_item_layout, viewGroup, false)
        return NoteAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: NoteAdapter.ViewHolder, i: Int) {
        val note = notesList[i]
        viewHolder.itemView.tvItemNoteTitle.text = note.title
        viewHolder.itemView.tvItemNoteDate.text = formatDate(note.changedDate)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}