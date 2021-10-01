package com.example.noteapp.feature_note.presentation.notes_list_screen

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.Order
import com.example.noteapp.feature_note.domain.util.Sort

/**
 * This data class will represent a particular state of note list screen.
 */
data class NoteState(
    val notes: List<Note> = emptyList(),
    val sort: Sort = Sort.Date(Order.DescendingOrder),
    val isFilterSectionVisible : Boolean = false
)
