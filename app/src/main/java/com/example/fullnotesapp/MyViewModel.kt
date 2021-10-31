package com.example.fullnotesapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val rep: MotherDb
    private val notes: LiveData<List<NoteData>>

    init {
        val noteDao = SQLdb.getDatabase(application).noteDao()
        rep = MotherDb(noteDao)
        notes = rep.getNotes
    }

    fun getNotes(): LiveData<List<NoteData>>{
        return notes
    }

    fun postNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            rep.addNote(NoteData(0, noteText))

        }
    }

    fun editNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            rep.updateNote(NoteData(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            rep.deleteNote(NoteData(noteID,""))
        }
    }
}