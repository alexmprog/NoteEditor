package com.renovavision.noteeditor.utils

import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by Alexandr Golovach on 07.02.18.
 */

class EventBus {

    val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    inline fun <reified T : Any> observe(): Observable<T> {
        return bus.ofType(T::class.java)
    }
}