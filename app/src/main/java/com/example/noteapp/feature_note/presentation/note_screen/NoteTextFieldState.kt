package com.example.noteapp.feature_note.presentation.note_screen

/**
 * Class represent text field state
 */
data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
