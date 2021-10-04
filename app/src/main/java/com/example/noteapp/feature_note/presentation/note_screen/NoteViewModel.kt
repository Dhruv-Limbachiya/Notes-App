package com.example.noteapp.feature_note.presentation.note_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.model.Note.*
import com.example.noteapp.feature_note.domain.use_cases.UseCases
import com.example.noteapp.feature_note.presentation.note_screen.NoteEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * A ViewModel class for adding a new note and updating the existing note.
 */
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val usecase: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Hold the note title
    private var _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitle = _noteTitle

    // Hold the note content
    private var _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter content..."
        )
    )
    val noteContent = _noteContent

    // Hold the note color
    private var _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor = _noteColor

    // Hold the UI Event.
    private var _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow() // Make _event as read only using asShareFlow() extension fun

    private var currentNoteId: Int? = null


    init {
        // Initialize all values when user wants to update existing note.
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    currentNoteId = noteId
                    val note = usecase.getSpecificNote(noteId) // get the specific note by id.
                    note?.let { it ->
                        _noteColor.value = it.color
                        _noteTitle.value =
                            _noteTitle.value.copy(text = it.title, isHintVisible = false)
                        _noteContent.value =
                            _noteContent.value.copy(text = it.content, isHintVisible = false)
                    }
                }
            }
        }
    }

    /**
     * Perform appropriate actions base on event performed by user.
     */
    fun onEvent(event: NoteEvent) {
        when (event) {
            is ChangeColor -> {
                _noteColor.value = event.color
            }

            is ChangeTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.title
                )
            }

            is ChangeContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.content
                )
            }

            is ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    // Hint should be visible when there is a blank title and has not focus on title text field.
                    isHintVisible = _noteTitle.value.text.isBlank() && !event.focusState.isFocused
                )
            }

            is ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    // Hint should be visible when there is a blank title and has not focus on title text field.
                    isHintVisible = _noteContent.value.text.isBlank() && !event.focusState.isFocused
                )
            }

            is SaveNote -> {
                viewModelScope.launch {
                    val note = Note(
                        noteId = currentNoteId,
                        title = _noteTitle.value.text,
                        content = _noteContent.value.text,
                        color = _noteColor.value,
                        timeStamp = System.currentTimeMillis()
                    )
                    try {
                        usecase.addNote(note)
                        _event.emit(UiEvent.SaveNote) // Emit save note event to observer
                    } catch (e: InvalidNoteException) {
                        _event.emit(
                            UiEvent.ShowSnackBar(
                                e.message ?: "Couldn't save the note"
                            )
                        ) // Emit show snack bar event to observer
                    }
                }
            }
        }
    }

    /**
     * A helper sealed class which will help to trigger snack bar event when there is an exception in saving note
     * & save note event when the note is saved in db successfully.
     */
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}