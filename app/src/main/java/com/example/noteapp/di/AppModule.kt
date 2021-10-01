package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * App Level module. Provide dependencies as long as app lives.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide database.
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NoteDatabase.DATABASE_NAME
    ).build()

    /**
     * Provide NoteRepository. It will provide implementation of NoteRepositoryImpl.
     */
    @Singleton
    @Provides
    fun provideNoteRepository(noteDatabase: NoteDatabase) : NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }


    /**
     * Provides UseCases dependency/instance.
     */
    @Singleton
    @Provides
    fun provideUseCases(noteRepository: NoteRepository) : UseCases {
        return UseCases(
            addNote = AddNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            getNotes = GetNotes(noteRepository),
            getSpecificNote = GetSpecificNote(noteRepository)
        )
    }
}