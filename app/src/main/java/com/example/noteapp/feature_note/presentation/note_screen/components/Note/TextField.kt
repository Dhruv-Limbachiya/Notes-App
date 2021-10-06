package com.example.noteapp.feature_note.presentation.note_screen.components.Note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle

/**
 * Re-usable composable for note title and note content.
 */
@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    textStyle: TextStyle,
    isSingleLine: Boolean,
    testTag: String = "",
    onValueChange: (String) -> Unit,
    onFocusStateChanged: (FocusState) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            singleLine = isSingleLine,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusStateChanged(it) }
                .testTag(testTag)
        )

        // Show hint if isHintVisible = true.
        if (isHintVisible) {
            Text(
                text = hint,
                color = Color.DarkGray,
                style = textStyle
            )
        }
    }
}