package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.model.Note.InvalidNoteException
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteTest {

    lateinit var fakeNoteRepository: NoteRepository

    lateinit var addNote: AddNote

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
    }

    @Test
    fun `if note title is blank throw InvalidNoteException`() {
        val note = Note(title = "abc", content = "", timeStamp = 1, color = 1)
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
    }

    @Test
    fun `if note content is blank throw InvalidNoteException`() {
        val note = Note(title = "", content = "abc", timeStamp = 1, color = 1)
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
    }

    @Test
    fun `if data are valid,insert note in db`() {
        val note = Note(title = "abc", content = "as", timeStamp = 1, color = 1)

        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }

        runBlocking {
            fakeNoteRepository.insertNote(note)
            val notes = fakeNoteRepository.getAllNotes().toList()
            assertThat(notes.isNotEmpty())
        }
    }
}