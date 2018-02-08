package com.renovavision.noteeditor.presentation.common.di;

import android.content.Context
import com.renovavision.noteeditor.utils.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    @Singleton
    fun providesEventBus(): EventBus {
        return EventBus()
    }

}
