package com.example.fullnotesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
abstract class SQLdb: RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile  //
        private var INSTANCE: SQLdb? = null

        fun getDatabase(context: Context): SQLdb{
            val tempoInst = INSTANCE
            if(tempoInst != null){
                return tempoInst
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SQLdb::class.java,
                    "notes_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}