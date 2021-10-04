package com.example.noteapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NoteListScreen : Screen(NOTE_LIST_SCREEN)
    object NoteScreen : Screen(ADD_EDIT_NOTE_SCREEN)

    companion object {
        const val NOTE_LIST_SCREEN = "notes_screen"
        const val ADD_EDIT_NOTE_SCREEN = "add_edit_note_screen"
    }
}
