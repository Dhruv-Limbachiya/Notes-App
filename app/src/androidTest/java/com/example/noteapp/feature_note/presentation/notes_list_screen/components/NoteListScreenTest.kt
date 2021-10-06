package com.example.noteapp.feature_note.presentation.notes_list_screen.components

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.noteapp.core.TestTag
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_note.presentation.MainActivity
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
class NoteListScreenTest {

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
                }
            }
        }
    }

    /**
     * Make sure that clicking toggle icon make order section visible.
     */
    @Test
    fun clickToggleOrderSection_isVisible() {
        // Make sure it doesn't exist(hide) initially.
        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertDoesNotExist()
        // perform click on the toggle icon button.
        composeRule.onNodeWithContentDescription("Sort").performClick()
        // Maker sure it is displayed after clicking on toggle icon button.
        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertIsDisplayed()
    }

}