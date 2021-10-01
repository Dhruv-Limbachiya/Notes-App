package com.example.noteapp.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * It represent a single record in Notes Database.
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId : Long? = null,
    var title: String,
    var content: String,
    var color: Int
)
