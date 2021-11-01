package com.example.fullnotesapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {
    //private val rep: MotherDb

    private val notes: MutableLiveData<List<NoteData>> = MutableLiveData()
    private var db = Firebase.firestore
    //init {
        //val noteDao = SQLdb.getDatabase(application).noteDao()
      //  rep = MotherDb(noteDao)
    //    notes = rep.getNotes
  //  }

    fun getNotes(): LiveData<List<NoteData>> {
        return notes
    }


    private fun getData(){
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                val tempNotes = arrayListOf<NoteData>()
                for (document in result) {
                    document.data.map {
                            (key, value) -> tempNotes.add(NoteData(document.id, value.toString()))
                    }
                }
                notes.postValue(tempNotes)
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }}

    fun postNote(text: NoteData){
        CoroutineScope(Dispatchers.IO).launch {
            //delay(1000)
            val newNote = hashMapOf("noteText" to text.notesText,)
            db.collection("notes").add(newNote)
            getData()
            //rep.addNote(NoteData(0, noteText))

        }
    }

    fun editNote(noteID: String, text: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id == noteID){
                            db.collection("notes").document(noteID).update("noteText", text)
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }
    }}

    fun deleteNote(noteID: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id == noteID){
                            db.collection("notes").document(noteID).delete()
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }

            //rep.deleteNote(NoteData(noteID,""))
        }
    }
}