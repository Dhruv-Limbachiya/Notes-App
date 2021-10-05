package com.example.noteapp.feature_note.presentation.notes_list_screen.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.core.TestTag
import com.example.noteapp.core.TestTag.NOTE_ITEM
import com.example.noteapp.feature_note.domain.util.Order
import com.example.noteapp.feature_note.domain.util.Sort
import com.example.noteapp.feature_note.presentation.notes_list_screen.NoteListEvent
import com.example.noteapp.feature_note.presentation.notes_list_screen.NotesViewModel
import com.example.noteapp.feature_note.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteState = viewModel.noteState.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.ADD_EDIT_NOTE_SCREEN)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Heading section
            HeadingSection(modifier = Modifier.fillMaxWidth())

            // Sort section (Radio buttons)
            AnimatedVisibility(
                visible = noteState.isFilterSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(modifier = Modifier.fillMaxWidth().testTag(TestTag.ORDER_SECTION), sort = noteState.sort)
            }

            // List (Notes)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(noteState.notes) { note ->
                    Spacer(modifier = Modifier.height(16.dp))
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Navigate to note screen.
                                navController.navigate(Screen.ADD_EDIT_NOTE_SCREEN + "?noteId=${note.noteId}&noteColor=${note.color}")
                            }
                            .testTag(NOTE_ITEM)
                    ) {
                        viewModel.onEvent(NoteListEvent.DeleteNote(note))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note deleted!",
                                actionLabel = "Undo"
                            )

                            // If undo action performed then restore the recently deleted note.
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(NoteListEvent.RestoreNote)
                            }
                        }
                    }

                }
            }
        }
    }
}


/**
 * Composable will render "Your Notes" label as heading and sort icon on left.
 */
@Composable
fun HeadingSection(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.text_heading),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

        IconButton(onClick = {
            viewModel.onEvent(NoteListEvent.ToggleFilter)
        }) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = "Sort"
            )
        }
    }
}

@Composable
fun SortSection(
    modifier: Modifier,
    sort: Sort = Sort.Date(Order.DescendingOrder),
    viewModel: NotesViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        // Row will render radio buttons with label "Date","Title" and "Color".
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NoteRadioButton(
                text = stringResource(R.string.text_title),
                isSelected = sort is Sort.Title // if sort is of type Title select radio button else unselect it.
            ) {
                // Update sort type to "Title".
                viewModel.onEvent(NoteListEvent.SortList(Sort.Title(sort.order)))
            }

            NoteRadioButton(
                text = stringResource(R.string.text_date),
                isSelected = sort is Sort.Date // if sort is of type "Date" select radio button else unselect it.
            ) {
                // Update sort type to "Date".
                viewModel.onEvent(NoteListEvent.SortList(Sort.Date(sort.order)))
            }

            NoteRadioButton(
                text = stringResource(R.string.text_color),
                isSelected = sort is Sort.Color // if sort is of type "Color" select radio button else unselect it.
            ) {
                // Update sort type to "Color".
                viewModel.onEvent(NoteListEvent.SortList(Sort.Color(sort.order)))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row will render radio buttons with label "Ascending" and "Descending".
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            NoteRadioButton(
                text = stringResource(R.string.text_ascending),
                isSelected = sort.order is Order.AscendingOrder // if order is of type "Ascending" select radio button else unselect it.
            ) {
                // Update sort order type to "AscendingOrder".
                viewModel.onEvent(NoteListEvent.SortList(sort.updateOrder(Order.AscendingOrder)))
            }

            Spacer(modifier = Modifier.width(8.dp))

            NoteRadioButton(
                text = stringResource(R.string.text_descending),
                isSelected = sort.order is Order.DescendingOrder // if order is of type "Descending" select radio button else unselect it.
            ) {
                // Update sort order type to "DescendingOrder".
                viewModel.onEvent(NoteListEvent.SortList(sort.updateOrder(Order.DescendingOrder)))
            }

        }
    }
}