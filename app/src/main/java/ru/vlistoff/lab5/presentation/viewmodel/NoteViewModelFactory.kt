package ru.vlistoff.lab5.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vlistoff.lab5.domain.NoteUseCase

class NoteViewModelFactory(private val noteUseCase: NoteUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {

            return NoteViewModel(noteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

