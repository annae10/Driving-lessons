package com.harman.drivinglessons.repository

import androidx.lifecycle.LiveData
import com.harman.drivinglessons.data.NoteDao
import com.harman.drivinglessons.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
}