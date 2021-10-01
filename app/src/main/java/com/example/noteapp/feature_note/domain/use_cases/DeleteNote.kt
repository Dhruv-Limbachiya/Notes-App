package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository


/**
 * Delete Note : This use case will be responsible for deleting a specific note in db using repository.
 */
class DeleteNote(
    private val noteRepository: NoteRepository
) {

    /**
     * Override the invoke() operator functionality.
     * Can be call using deleteNoteObject.invoke(note) or deleteNoteObject(note).
     */
    suspend operator fun invoke(noteToDelete: Note) {
        noteRepository.deleteNote(noteToDelete)
    }
}