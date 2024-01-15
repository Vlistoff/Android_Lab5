package ru.vlistoff.lab5.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.vlistoff.lab5.domain.NoteModel
import ru.vlistoff.lab5.domain.NoteUseCase
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(private val noteUseCase: NoteUseCase) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteModel>>()

    val notes: LiveData<List<NoteModel>>
        get() = _notes

    fun addNote(title: String, text: String) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            val formattedDate = dateFormat.format(Date())

            val note = NoteModel(0, title = title, text = text, date = formattedDate)
            noteUseCase.addNoteUseCase(note)
        }
        loadNotes()
    }

    fun editNote(note: NoteModel) {
        viewModelScope.launch {
            noteUseCase.updateNoteUseCase(note)
        }
        loadNotes()
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            noteUseCase.deleteNoteUseCase(note)
        }
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            val notes = noteUseCase.getNotesUseCase()
            _notes.value = notes
        }
    }
}
