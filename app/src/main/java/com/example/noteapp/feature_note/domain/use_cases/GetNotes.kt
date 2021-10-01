package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.Order
import com.example.noteapp.feature_note.domain.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Get Notes : This use case will be responsible for fetching all the notes record from the database ,
 * according to the user given sort/filter preference.
 */
class GetNotes(
    private val noteRepository: NoteRepository
) {

    /**
     * Override the invoke() operator functionality.
     * Can be call using getNotesObject.invoke() or getNotesObject() and return Flow of note list.
     */
    operator fun invoke(sort: Sort = Sort.Date(Order.DescendingOrder)): Flow<List<Note>> {
        return noteRepository.getAllNotes().map { notes ->
            when (sort.order) {
                // If user select list order as ascending.
                is Order.AscendingOrder -> {
                    // Check the user selected sorting
                    when (sort) {
                        is Sort.Date -> notes.sortedBy { it.timeStamp } // Sort by date in ascending order.
                        is Sort.Title -> notes.sortedBy { it.title } // Sort by title in ascending order
                        is Sort.Color -> notes.sortedBy { it.color } // Sort by color in ascending order
                    }
                }
                // If user select list order as descending.
                is Order.DescendingOrder -> {
                    // Check the user selected sorting
                    when (sort) {
                        is Sort.Date -> notes.sortedByDescending { it.timeStamp } // Sort by date in descending order.
                        is Sort.Title -> notes.sortedByDescending { it.title } // Sort by title in descending order
                        is Sort.Color -> notes.sortedByDescending { it.color } // Sort by color in descending order
                    }
                }
            }
        }
    }
}