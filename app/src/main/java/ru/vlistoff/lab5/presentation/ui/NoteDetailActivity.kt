package ru.vlistoff.lab5.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking
import ru.vlistoff.lab5.R
import ru.vlistoff.lab5.data.Note
import ru.vlistoff.lab5.data.NoteDao
import ru.vlistoff.lab5.data.NoteDatabase
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var noteEditTitle: EditText
    private lateinit var noteEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_detail)

        val noteId = intent.getIntExtra("noteId", -1)

        noteEditTitle = findViewById(R.id.titleEditText)
        noteEditText = findViewById(R.id.textEditText)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        saveButton = findViewById(R.id.saveButton)

        val noteDatabase = NoteDatabase.newInstance(applicationContext)

        val noteDao = noteDatabase.noteDao()

        val noteFromDatabase: Note = getNoteFromDatabase(noteId, noteDao)
        noteEditTitle.setText(noteFromDatabase.title)
        noteEditText.setText(noteFromDatabase.text)
        dateTextView.text = noteFromDatabase.date

        saveButton.setOnClickListener {
            val updatedTitle = noteEditTitle.text.toString()
            noteFromDatabase.title = updatedTitle

            val updatedText = noteEditText.text.toString()
            noteFromDatabase.text = updatedText

            val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
            val formattedDate = dateFormat.format(Date())

            noteFromDatabase.date = formattedDate

            val resultIntent = Intent()
            resultIntent.putExtra("editedNote", noteFromDatabase.toModel())

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun getNoteFromDatabase(noteId: Int, noteDao: NoteDao): Note {
        var noteFromDatabase: Note
        runBlocking {
            noteFromDatabase = noteDao.getNoteById(noteId)!!
        }

        return noteFromDatabase
    }

}
