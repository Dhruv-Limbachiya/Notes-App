package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
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

}