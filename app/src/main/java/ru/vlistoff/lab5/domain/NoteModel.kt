package ru.vlistoff.lab5.domain

import ru.vlistoff.lab5.data.Note
import java.io.Serializable

data class NoteModel(
    var id: Int,
    var title: String,
    var text: String,
    var date: String
) : Serializable {
    fun toEntity() : Note {
        return Note (id, title, text, date)
    }
}
