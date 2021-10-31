package com.example.fullnotesapp

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface Notat {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteData)

    @Query("SELECT * FROM NotesTable ORDER BY id DESC")
    fun getNotes(): LiveData<List<NoteData>>

    @Update
    suspend fun updateNote(note: NoteData)

    @Delete
    suspend fun deleteNote(note: NoteData)

}
