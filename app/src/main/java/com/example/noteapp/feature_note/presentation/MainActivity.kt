package com.example.noteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.feature_note.presentation.note_screen.components.NoteScreen
import com.example.noteapp.feature_note.presentation.notes_list_screen.components.NoteListScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NOTE_LIST_SCREEN
                    ) {
                        composable(route = Screen.NOTE_LIST_SCREEN) {
                            NoteListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.ADD_EDIT_NOTE_SCREEN + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument("noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val noteColor = it.arguments?.getInt("noteColor") ?: -1

                            NoteScreen(
                                navController = navController,
                                noteColor = noteColor
                            )
                        }
                    }
                }
            }
        }
    }
}
