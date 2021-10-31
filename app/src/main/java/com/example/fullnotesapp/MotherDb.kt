package com.example.fullnotesapp

import androidx.lifecycle.LiveData


class MotherDb(private val notat: Notat) {

    val getNotes: LiveData<List<NoteData>> = notat.getNotes()

    suspend fun addNote(note: NoteData){
        notat.addNote(note)
    }

    suspend fun updateNote(note: NoteData){
        notat.updateNote(note)
    }

    suspend fun deleteNote(note: NoteData){
        notat.deleteNote(note)
    }

}