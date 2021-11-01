package com.example.fullnotesapp

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvNotes: RecyclerView
    private lateinit var mainRV: MyAdapter
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button
    private lateinit var notesList: ArrayList<NoteData>
    lateinit var myViewModel: MyViewModel
    //private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Notebook"

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getNotes().observe(this, { notes -> mainRV.update(notes) })
        //myViewModel = ViewModelProvider().get(MyViewModel::class.java)


        notesList = arrayListOf()
        editText = findViewById(R.id.tvNewNote)
        submitBtn = findViewById(R.id.btSubmit)

        submitBtn.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                myViewModel.postNote(NoteData("",editText.text.toString()))
                editText.text.clear()
                editText.clearFocus()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error: Please Enter something!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        rvNotes = findViewById(R.id.rvNotes)
        mainRV = MyAdapter(this)
        rvNotes.adapter = mainRV
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    fun raiseDialog(id: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                //db.collection("notes").document(id).update("noteText", notesList)
            myViewModel.editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}