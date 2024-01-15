package ru.vlistoff.lab5.domain

class NoteUseCaseImpl(private val repository: NoteRepository) : NoteUseCase {

    override suspend fun getNotesUseCase(): List<NoteModel> {
        return repository.getNotes()
    }

    override suspend fun addNoteUseCase(note: NoteModel) {
        return repository.addNote(note)
    }

    override suspend fun updateNoteUseCase(note: NoteModel) {
        return repository.updateNote(note)
    }

    override suspend fun deleteNoteUseCase(note: NoteModel) {
        return repository.deleteNote(note)
    }

}