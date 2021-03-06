package com.example.noteapp.feature_note.presentation.notes_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_cases.UseCases
import com.example.noteapp.feature_note.domain.util.Order
import com.example.noteapp.feature_note.domain.util.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val usecase: UseCases
) : ViewModel() {

    // State for holding current note list screen state.
    private var _noteState = mutableStateOf(NoteState())
    val noteState: State<NoteState> = _noteState

    var deletedNote: Note? = null

    private var job: Job? = null

    init {
        getNotes(Sort.Date(Order.DescendingOrder))
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.SortList -> {
                /**
                 * Check if the user select same order and sort again then do nothing.
                 * Ex : sort by date in asc (Existing state value) ,sort by date in asc (Updated state value).
                 * Do nothing in this kind off cases.
                 */
                if (event.sort.order::class == noteState.value.sort::class && event.sort::class == noteState.value.sort::class) {
                    return
                }

                getNotes(event.sort)
            }

            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    usecase.deleteNote(event.note) // Invoke DeleteNote use-case with note to delete.
                    deletedNote = event.note
                }
            }

            is NoteListEvent.RestoreNote -> {
                deletedNote?.let { noteToRestore ->
                    viewModelScope.launch {
                        usecase.addNote(noteToRestore) // Add the deleted note again.
                    }
                }
            }

            is NoteListEvent.ToggleFilter -> {
                _noteState.value = _noteState.value.copy(
                    isFilterSectionVisible = !_noteState.value.isFilterSectionVisible // Toggle the isFilterSectionVisible value
                )
            }
        }
    }

    /**
     * Get notes from the db by invoking "getNotes(sort)" use-case.
     */
    private fun getNotes(sort: Sort) {
        job?.cancel()
        job = usecase.getNotes(sort).onEach { notes ->
            // update note list and its order in note state value.
            _noteState.value = _noteState.value.copy(
                notes = notes,
                sort = sort
            )
        }.launchIn(viewModelScope)
    }
}