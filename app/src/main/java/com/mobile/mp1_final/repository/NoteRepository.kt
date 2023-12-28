package com.mobile.mp1_final.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mobile.mp1_final.database.Note
import com.mobile.mp1_final.database.NoteDao
import com.mobile.mp1_final.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {

    private var mNoteDao:NoteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNoteDao = db.noteDao()
    }

    private val insertionStatus = MutableLiveData<Boolean>()

    fun insertIfNotExists(name: String, species: String, img: String) {
        executorService.execute {
            val existingNote = mNoteDao.getNoteByNameLevelAndImg(name, species, img)
            if (existingNote == null) {
                val newNote = Note(name = name, species = species, img = img)
                mNoteDao.insert(newNote)
                insertionStatus.postValue(true)
            }
            else {
                insertionStatus.postValue(false)
            }
        }
    }

    fun deleteExistingNote(name: String, species: String, img: String) {
        executorService.execute {
            val existingNote = mNoteDao.getNoteByNameLevelAndImg(name, species, img)
            existingNote?.let {
                mNoteDao.delete(it)
            }
        }
    }

    fun update(note: Note) {
        executorService.execute {
            mNoteDao.update(note)
        }
    }
}