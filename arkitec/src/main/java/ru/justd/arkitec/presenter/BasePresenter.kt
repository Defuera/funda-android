package ru.justd.arkitec.presenter

import rx.Completable
import rx.Observable
import rx.Single
import rx.SingleSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by defuera on 01/02/2017.
 */
abstract class BasePresenter<V> {

    private var view: V? = null
    private val subscriptions = CompositeSubscription()

    fun attachView(view: V) {
        this.view = view
        onViewAttached()
    }

    fun detachView() {
        this.view = null
        subscriptions.clear()
        onViewDetached()
    }

    fun view() = view!!

    abstract fun onViewAttached()

    open fun onViewDetached(){}

    fun <T> subscribe(
            observable: Observable<T>,
            onNext: Action1<T> = Action1 { },
            onError: Action1<Throwable> = Action1(Throwable::printStackTrace),
            onCompleted: Action0 = Action0 { }) {
        subscriptions.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(onNext, onError, onCompleted)
        )
    }

    fun <T> subscribe(
            single: Single<T>,
            singleSubscriber: SingleSubscriber<T>
    ) {
        subscriptions.add(
                single.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(singleSubscriber)
        )
    }

    fun <T> subscribe(
            single: Single<T>,
            onSuccess: Action1<T> = Action1 { },
            onError: Action1<Throwable> = Action1(Throwable::printStackTrace)) {
        subscriptions.add(
                single.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccess, onError)
        )
    }

    fun subscribe(
            completable: Completable,
            onSuccess: Action0 = Action0 { },
            onError: Action1<Throwable> = Action1 { }) {
        subscriptions.add(
                completable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccess, onError))
    }
}