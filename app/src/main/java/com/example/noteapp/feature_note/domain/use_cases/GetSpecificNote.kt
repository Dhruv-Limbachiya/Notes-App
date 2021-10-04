package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository


/**
 * Get a Specific Note : This use case will be responsible for fetching a specific note record from the db using repository.
 */
class GetSpecificNote(
    private val noteRepository: NoteRepository
) {

    /**
     * Override the invoke() operator functionality.
     * Can be call using getNoteObject.invoke(id) or getNoteObject(id) and return a note.
     */
    suspend operator fun invoke(noteId: Int) : Note?{
      return  noteRepository.getNoteById(noteId)
    }
}