package ru.vlistoff.lab5.domain

interface NoteUseCase {
    suspend fun getNotesUseCase(): List<NoteModel>
    suspend fun addNoteUseCase(note: NoteModel)
    suspend fun updateNoteUseCase(note: NoteModel)
    suspend fun deleteNoteUseCase(note: NoteModel)
}