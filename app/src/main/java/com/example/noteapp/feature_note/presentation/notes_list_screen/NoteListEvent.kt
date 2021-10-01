package com.example.noteapp.feature_note.presentation.notes_list_screen

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.Sort

/**
 * Events a user can perform on Note list screen.


 */
sealed class NoteListEvent {
    data class SortList(val sort: Sort) : NoteListEvent()
    data class DeleteNote(val note: Note) : NoteListEvent()
    object RestoreNote : NoteListEvent()
    object ToggleFilter : NoteListEvent()
}