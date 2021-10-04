package com.example.noteapp.feature_note.presentation.note_screen.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presentation.note_screen.NoteEvent
import com.example.noteapp.feature_note.presentation.note_screen.NoteViewModel
import com.example.noteapp.feature_note.presentation.note_screen.NoteViewModel.UiEvent
import com.example.noteapp.feature_note.presentation.note_screen.components.Note.NoteTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val noteTitle = viewModel.noteTitle.value

    val noteContent = viewModel.noteContent.value

    val backgroundColor = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val uiEvent = viewModel.event

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        // Observe the ui events.
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvent.SaveNote -> {
                    navController.navigateUp() // Navigate to the previous screen.
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(NoteEvent.SaveNote)
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note",
                    tint = MaterialTheme.colors.background
                )
            }
        },
        scaffoldState = scaffoldState,
        backgroundColor = backgroundColor.value
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            ColorBubbles(noteColor = noteColor) { color ->
                coroutineScope.launch {
                    backgroundColor.animateTo(
                        targetValue = color,
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Note Title
            NoteTextField(
                text = noteTitle.text,
                hint = noteTitle.hint,
                textStyle = MaterialTheme.typography.h5,
                isSingleLine = true,
                onValueChange = { text ->
                    viewModel.onEvent(NoteEvent.ChangeTitle(text))
                },
                onFocusStateChanged = { focusState ->
                    viewModel.onEvent(NoteEvent.ChangeTitleFocus(focusState))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Note Content
            NoteTextField(
                text = noteContent.text,
                hint = noteContent.hint,
                textStyle = MaterialTheme.typography.body1,
                isSingleLine = false,
                onValueChange = { text ->
                    viewModel.onEvent(NoteEvent.ChangeContent(text))
                },
                onFocusStateChanged = { focusState ->
                    viewModel.onEvent(NoteEvent.ChangeContentFocus(focusState))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

/**
 * Composable for rendering color bubble(note background color selector).
 */
@Composable
fun ColorBubbles(
    modifier: Modifier = Modifier,
    noteColor: Int,
    onColorChange: (Color) -> Unit
) {
    Note.noteColors.forEach { color ->
        Box(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (noteColor == color.toArgb()) {
                        Color.DarkGray
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
                .background(
                    color = color,
                    shape = CircleShape
                )
                .clickable {
                    onColorChange(color)
                }
        )
    }
}

