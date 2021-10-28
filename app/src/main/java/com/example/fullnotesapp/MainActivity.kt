package com.example.fullnotesapp

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
//    private lateinit var db: SQLdb

    private lateinit var rvNotes: RecyclerView
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button
    private lateinit var notesList: List<NoteData>
    private val notesDao by lazy {
        SQLdb.getDatabase(this).noteDao()
    }
    private val db by lazy {
        MotherDb(notesDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //db = SQLdb(this)
        notesList = listOf()
        editText = findViewById(R.id.tvNewNote)
        submitBtn = findViewById(R.id.btSubmit)

        submitBtn.setOnClickListener {
            postNote(editText.text.toString())
            editText.text.clear()
            editText.clearFocus()
            updateRV()
        }

        getItemsList()

        rvNotes = findViewById(R.id.rvNotes)
        updateRV()
    }

    private fun updateRV(){
        rvNotes.adapter = MyAdapter(this, notesList)
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList() {
        CoroutineScope(IO).launch {
            val data = withContext(Dispatchers.Default) {
                db.getNotes
            }
            if(data.isNotEmpty()){
                notesList = data
                updateRV()
            }
        }
    }

    private  fun postNote(noteText: String) {
        CoroutineScope(IO).launch {
            db.addNote(NoteData(0, noteText))

        }
        Toast.makeText(applicationContext, "Note added!", Toast.LENGTH_LONG).show()
    }

    private fun editNote(noteID: Int, noteText: String){
        CoroutineScope(IO).launch {
            db.updateNote(NoteData(noteID,noteText))
        }
        Toast.makeText(applicationContext, "Note Updated!", Toast.LENGTH_LONG).show()
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(IO).launch {
            db.deleteNote(NoteData(noteID,""))
        }
        Toast.makeText(applicationContext, "Note Deleted!", Toast.LENGTH_LONG).show()

    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}