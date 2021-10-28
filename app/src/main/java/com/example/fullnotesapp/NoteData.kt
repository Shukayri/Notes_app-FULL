package com.example.fullnotesapp


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotesTable")
    data class NoteData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val notesText: String)