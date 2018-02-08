package com.renovavision.noteeditor.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.renovavision.noteeditor.data.di.DataModule
import com.renovavision.noteeditor.presentation.common.di.AppComponent
import com.renovavision.noteeditor.presentation.common.di.AppModule
import com.renovavision.noteeditor.presentation.common.di.DaggerAppComponent

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

class App : Application() {

    companion object {
        lateinit var graph: AppComponent

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        graph = DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .dataModule(DataModule())
                .build()
    }
}