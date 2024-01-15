package ru.vlistoff.lab5.data

import android.util.Log
import ru.vlistoff.lab5.domain.NoteModel
import ru.vlistoff.lab5.domain.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun getNotes(): List<NoteModel> {
        return noteDao.getNotes().map {
            it.toModel()
        }
    }

    override suspend fun addNote(note: NoteModel) {
        return noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: NoteModel) {
        Log.d("RepoImpl", note.toEntity().toString())
        return noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: NoteModel) {
        Log.d("RepoImpl", note.toEntity().toString())
        return noteDao.deleteNote(note.toEntity())
    }
}