package com.renovavision.noteeditor.presentation.common.di

import com.renovavision.noteeditor.data.di.DataModule
import com.renovavision.noteeditor.presentation.ui.details.NoteDetailsPresenter
import com.renovavision.noteeditor.presentation.ui.note.NoteListPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Singleton
@Component(modules = [(DataModule::class), (AppModule::class)])
interface AppComponent {

    fun inject(presenter: NoteListPresenter)

    fun inject(presenter: NoteDetailsPresenter)

}