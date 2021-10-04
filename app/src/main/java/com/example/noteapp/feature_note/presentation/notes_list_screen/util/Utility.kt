package com.example.noteapp.feature_note.presentation.notes_list_screen.util

import java.text.SimpleDateFormat
import java.util.*

fun convertTimeStampToDate(timeStamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
    return "- ${simpleDateFormat.format(timeStamp)}"
}