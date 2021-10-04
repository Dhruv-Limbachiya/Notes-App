package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.model.Note.*
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
        if(noteToInsert.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(noteToInsert.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        noteRepository.insertNote(noteToInsert)
    }
}