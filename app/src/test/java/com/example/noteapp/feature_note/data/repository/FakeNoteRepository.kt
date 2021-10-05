package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This FakeNoteRepository class helps to test the functionality all the operation perfor
 */
class FakeNoteRepository : NoteRepository {

    val notes = mutableListOf<Note>()

    override fun getAllNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        return notes[noteId]
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}