package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

/**
 * AddNote : This use case will be responsible inserting a user note into db using repository.
 */
class AddNote(
    private val noteRepository: NoteRepository
) {

    /**
     * Override the invoke() operator functionality.
     * Can be call using addNoteObject.invoke(note) or addNoteObject(note).
     */
    suspend operator fun invoke(noteToInsert: Note) {
        noteRepository.insertNote(noteToInsert)
    }
}