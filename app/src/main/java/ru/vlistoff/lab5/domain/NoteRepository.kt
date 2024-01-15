package ru.vlistoff.lab5.domain

interface NoteRepository {
    suspend fun getNotes(): List<NoteModel>
    suspend fun addNote(note: NoteModel)
    suspend fun updateNote(note: NoteModel)
    suspend fun deleteNote(note: NoteModel)
}