package com.example.noteapp.feature_note.domain.use_cases

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.Order
import com.example.noteapp.feature_note.domain.util.Sort
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    lateinit var fakeNoteRepository: FakeNoteRepository
    lateinit var getNote: GetNotes

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        getNote = GetNotes(fakeNoteRepository)

        val fakeNotes = mutableListOf<Note>()

        // Add fake data in fake notes list.
        ('a'..'z').forEachIndexed { index, char ->
            fakeNotes.add(
                Note(
                    title = char.toString(),
                    content = char.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }

        fakeNotes.shuffle() // shuffle the list.

        runBlocking {
            fakeNotes.forEach { fakeNoteRepository.insertNote(it) } // Insert all notes in db
        }
    }

    @Test
    fun `sort notes by date in ascending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Date(Order.AscendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isLessThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `sort notes by date in descending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Date(Order.DescendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `sort notes by title in ascending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Title(Order.AscendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `sort notes by title in descending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Title(Order.DescendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }


    @Test
    fun `sort notes by color in ascending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Date(Order.AscendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `sort notes by color in descending order,correct order`() = runBlocking {
        val notes = getNote(Sort.Date(Order.DescendingOrder)).first()
        for(i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}