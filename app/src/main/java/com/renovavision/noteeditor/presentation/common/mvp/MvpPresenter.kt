package com.renovavision.noteeditor.presentation.common.mvp;

import android.os.Bundle

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

interface MvpPresenter<in V : MvpView> {

    fun attachView(mvpView: V)

    fun detachView()

    fun saveState(outBundle: Bundle?)

    fun restoreState(savedInstanceState: Bundle)
}
