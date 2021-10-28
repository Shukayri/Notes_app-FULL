package com.example.fullnotesapp


class NotesDatabase(private val notesDao: NotesDao) {

    val getNotes: List<NoteData> = notesDao.getNotes()

    suspend fun addNote(note: NoteData){
        notesDao.addNote(note)
    }

    suspend fun updateNote(note: NoteData){
        notesDao.updateNote(note)
    }

    suspend fun deleteNote(note: NoteData){
        notesDao.deleteNote(note)
    }

}