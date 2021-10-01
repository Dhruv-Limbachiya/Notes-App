package com.example.noteapp.feature_note.domain.repository

import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * An interface which can be tested using Fake Repository Impl.
 */
interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}