package ru.vlistoff.lab5.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import ru.vlistoff.lab5.R
import ru.vlistoff.lab5.data.NoteDatabase
import ru.vlistoff.lab5.data.NoteRepositoryImpl
import ru.vlistoff.lab5.domain.NoteModel
import ru.vlistoff.lab5.domain.NoteUseCaseImpl
import ru.vlistoff.lab5.presentation.adapter.NoteAdapter
import ru.vlistoff.lab5.presentation.viewmodel.NoteViewModel
import ru.vlistoff.lab5.presentation.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var noteEditText: EditText
    private lateinit var editedNote: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //получаем инстанс БД
        noteDatabase = NoteDatabase.newInstance(applicationContext)

        val noteDao = noteDatabase.noteDao()

        val repository = NoteRepositoryImpl(noteDao)
        val interactor = NoteUseCaseImpl(repository)
        runBlocking {
            Log.d("MainActivity", interactor.getNotesUseCase().toString())
        }

        viewModel = ViewModelProvider(this, NoteViewModelFactory(interactor))[NoteViewModel::class.java]
        viewModel.loadNotes()

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)
        noteEditText = findViewById(R.id.noteEditText)

        noteAdapter = NoteAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter

        addButton.setOnClickListener {
            val noteTitle = "Новая заметка"
            val noteText = noteEditText.text.toString()
            if (noteText.isNotBlank()) {
                viewModel.addNote(noteTitle, noteText)
                noteEditText.text.clear()
            }
        }

        val editNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    editedNote = result.data?.getSerializableExtra("editedNote", NoteModel::class.java)!!
                } else {
                    @Suppress("DEPRECATION")
                    editedNote = result.data?.getSerializableExtra("editedNote") as NoteModel
                }

                viewModel.editNote(editedNote)
            }
        }

        // Обработка нажатия на элемент списка (для редактирования)
        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: NoteModel) {
                val intent = Intent(this@MainActivity, NoteDetailActivity::class.java)
                intent.putExtra("noteId", note.id)
                editNoteLauncher.launch(intent)
            }
        })

        // Обработка долгого нажатия на элемент списка (для удаления)
        noteAdapter.setOnItemLongClickListener(object : NoteAdapter.OnItemLongClickListener {
            override fun onItemLongClick(note: NoteModel) {
                viewModel.deleteNote(note)
            }
        })

        viewModel.notes.observe(this) { notes ->
            noteAdapter.setNotes(notes)
            noteAdapter.notifyDataSetChanged()
        }
    }
}


