package ru.vlistoff.lab5.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.vlistoff.lab5.R
import androidx.recyclerview.widget.RecyclerView
import ru.vlistoff.lab5.domain.NoteModel

class NoteAdapter(private var notes: List<NoteModel>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.textTextView.text = note.text
        holder.dateTextView.text = note.date

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(note)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(note)
            true
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val textTextView: TextView = itemView.findViewById(R.id.textTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    fun setNotes(newNotes: List<NoteModel>) {
        notes = newNotes
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        onItemLongClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteModel)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(note: NoteModel)
    }
}

