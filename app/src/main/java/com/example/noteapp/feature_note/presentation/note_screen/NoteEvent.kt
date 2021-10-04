package com.example.noteapp.feature_note.presentation.note_screen

import androidx.compose.ui.focus.FocusState
import com.example.noteapp.feature_note.domain.model.Note

sealed class NoteEvent {
    data class ChangeColor(val color: Int) : NoteEvent()
    data class ChangeTitle(val title: String) : NoteEvent()
    data class ChangeContent(val content: String) : NoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : NoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : NoteEvent()
    object SaveNote : NoteEvent()
}