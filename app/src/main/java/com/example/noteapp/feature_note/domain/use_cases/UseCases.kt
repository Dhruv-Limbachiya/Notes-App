package com.example.noteapp.feature_note.domain.use_cases

/**
 * Wrapper class with all the possible use-cases.
 */
data class UseCases(
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getNotes: GetNotes,
    val getSpecificNote: GetSpecificNote
)
