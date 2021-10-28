package com.example.fullnotesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// click on key word and press Ctrl+Q to read more about them
@Database(entities = [NoteData::class], version = 1, exportSchema = false)
abstract class SQLdb: RoomDatabase() {

    abstract fun noteDao(): Notat

    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: SQLdb? = null

        fun getDatabase(context: Context): SQLdb{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SQLdb::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}