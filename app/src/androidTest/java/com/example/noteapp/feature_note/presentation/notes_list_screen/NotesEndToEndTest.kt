package com.example.noteapp.feature_note.presentation.notes_list_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.core.TestTag
import com.example.noteapp.core.TestTag.NOTE_ITEM
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_note.presentation.MainActivity
import com.example.noteapp.feature_note.presentation.note_screen.components.NoteScreen
import com.example.noteapp.feature_note.presentation.notes_list_screen.components.NoteListScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @ExperimentalAnimationApi
    @Before
    fun setup() {
        hiltRule.inject() // Inject all the dependencies.
        composeRule.setContent {
            NoteAppTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.NoteListScreen.route
                ) {
                    composable(route = Screen.NoteListScreen.route) {
                        NoteListScreen(navController = navHostController)
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
                            navController = navHostController,
                            noteColor = noteColor
                        )
                    }
                }
            }
        }
    }


    @Test
    fun saveNoteAndEditNote() {
        // Click on add note fab
        composeRule.onNodeWithContentDescription("Add note").performClick()

        // Enter text into title text field
        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("title-test")

        // Enter text into content text field
        composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD)
            .performTextInput("title-content")

        // Click on save note fab
        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Check the note with "title-test" or "content-test" is displayed in list.
        composeRule.onNodeWithText("title-test").assertIsDisplayed()
        composeRule.onNodeWithText("content-test").assertIsDisplayed()

        // click on that note.
        composeRule.onNodeWithText("title-test").performClick()

        // is the note data are the same data which note item in the list is showing.
        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).assertTextEquals("title-test")
        composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD).assertTextEquals("title-content")

        // Update title data
        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("2")

        // Save note by clicking on "Save note" FAB
        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Check the note title is updated or note
        composeRule.onNodeWithText("title-test2").assertIsDisplayed()
    }

    @Test
    fun addThreeNotes_sortByTitle_orderByDescending() {
        for (i in 0..3) {
            // Click on "Add note" FAB.
            composeRule.onNodeWithContentDescription("Add note").performClick()

            // Enter text into title text field
            composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).performTextInput(i.toString())

            // Enter text into content text field
            composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD).performTextInput(i.toString())

            // Click on "Save note" FAB
            composeRule.onNodeWithContentDescription("Save note").performClick()

            // Click Sort toggle icon button.
            composeRule.onNodeWithContentDescription("Sort").performClick()

            // Click "Title" & "Descending" radio button.
            composeRule.onNodeWithContentDescription("Title").performClick()
            composeRule.onNodeWithContentDescription("Descending").performClick()

            composeRule.onAllNodesWithTag(NOTE_ITEM)[0].assertTextContains("3")
            composeRule.onAllNodesWithTag(NOTE_ITEM)[1].assertTextContains("2")
            composeRule.onAllNodesWithTag(NOTE_ITEM)[2].assertTextContains("1")
        }
    }
}