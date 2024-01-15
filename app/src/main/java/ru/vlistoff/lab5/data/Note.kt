package ru.vlistoff.lab5.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vlistoff.lab5.domain.NoteModel
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "date")
    var date: String
) : Serializable {
    fun toModel() : NoteModel {
        return NoteModel (id, title, text, date)
    }
}
