package com.example.fullnotesapp

import androidx.room.*
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteData)

    @Query("SELECT * FROM NotesTable ORDER BY id DESC")
    fun getNotes(): List<NoteData>

    @Update
    suspend fun updateNote(note: NoteData)

    @Delete
    suspend fun deleteNote(note: NoteData)

}
