package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

/**
 * Class implement [NoteRepository] and performs actual database operation.
 */
class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes() = noteDao.getAllNotes()

    override suspend fun getNoteById(noteId: Int) = noteDao.getNoteById(noteId)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

}