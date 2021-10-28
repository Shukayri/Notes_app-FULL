package com.example.fullnotesapp


class MotherDb(private val notat: Notat) {

    val getNotes: List<NoteData> = notat.getNotes()

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