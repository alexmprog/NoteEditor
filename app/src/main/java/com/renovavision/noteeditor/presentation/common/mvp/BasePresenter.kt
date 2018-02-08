package com.renovavision.noteeditor.presentation.common.mvp

import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * Created by Alexandr Golovach on 29.01.18.
 */

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    private var mvpView: WeakReference<V>? = null

    protected val isViewAttached: Boolean
        get() = mvpView != null && mvpView!!.get() != null

    override fun attachView(mvpView: V) {
        this.mvpView = WeakReference(mvpView)
    }

    override fun detachView() {
        if (mvpView != null) {
            mvpView!!.clear()
        }
    }

    fun getMvpView(): V? {
        return if (mvpView != null) {
            mvpView!!.get()
        } else null
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

    override fun restoreState(savedInstanceState: Bundle) {
        // do nothing here - will implement in children classes
    }

    override fun saveState(outBundle: Bundle?) {
        // do nothing here - will implement in children classes
    }
}