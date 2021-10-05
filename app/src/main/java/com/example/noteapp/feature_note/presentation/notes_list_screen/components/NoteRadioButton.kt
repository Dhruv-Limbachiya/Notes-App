package com.example.noteapp.feature_note.presentation.notes_list_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun NoteRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected ,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary, // White.
                unselectedColor = MaterialTheme.colors.onBackground // Dark Gray.
            ),
            modifier = Modifier.semantics {
                contentDescription = text
            }
        )
        
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.body1,
        )
    }
}