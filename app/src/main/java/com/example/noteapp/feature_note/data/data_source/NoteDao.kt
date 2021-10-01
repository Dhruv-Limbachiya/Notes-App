package com.example.noteapp.feature_note.data.data_source

import androidx.room.*
import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Interface to access Notes Database.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes() : Flow<List<Note>>

    /**
     * This method will perform either insert or update operation
     * INSERT - If note has a unique id.
     * UPDATE - If note id is equal to existing note id in db.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}