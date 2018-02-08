package com.renovavision.noteeditor.data.di

import android.arch.persistence.room.Room
import android.content.Context
import com.renovavision.noteeditor.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(context: Context): NoteDatabase =
            Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase")
                    .allowMainThreadQueries()
                    .build()

    @Provides
    @Singleton
    fun providesNoteDao(database: NoteDatabase) = database.noteDao()
}