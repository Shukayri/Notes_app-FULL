package com.example.fullnotesapp


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotesTable")
    data class NoteData(
    @PrimaryKey(autoGenerate = true)
    //val int: id
    val id: String,
    val notesText: String) {
}