package com.example.noteapp.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.*
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * It represent a single record in Notes Database.
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId : Int? = null,
    var title: String,
    var content: String,
    var color: Int,
    var timeStamp: Long
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

    class InvalidNoteException(msg: String) : IllegalArgumentException(msg)
}
