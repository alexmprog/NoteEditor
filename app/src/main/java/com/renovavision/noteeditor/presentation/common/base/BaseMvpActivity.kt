package com.renovavision.noteeditor.presentation.common.base;

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.renovavision.noteeditor.presentation.common.mvp.MvpPresenter
import com.renovavision.noteeditor.presentation.common.mvp.MvpView

abstract class BaseMvpActivity<in V : MvpView, P : MvpPresenter<V>> : AppCompatActivity() {

    protected var mvpPresenter: P? = null

    protected var isFirstOpening: Boolean = false

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract fun createMvpPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutRes > 0 && inflateViewOnCreate()) {
            fillContentView()
        }

        mvpPresenter = createMvpPresenter()

        // restore state if needed
        if (savedInstanceState != null && mvpPresenter != null) {
            mvpPresenter!!.restoreState(savedInstanceState)
        }

        isFirstOpening = true
    }

    protected fun inflateViewOnCreate(): Boolean {
        return true
    }

    protected fun fillContentView() {
        setContentView(layoutRes)
    }

    override fun onResume() {
        super.onResume()
        isFirstOpening = false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mvpPresenter != null) {
            mvpPresenter!!.saveState(outState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpPresenter!!.detachView()
    }
}

