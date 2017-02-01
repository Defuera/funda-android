package ru.justd.fundaassignment

import android.content.Intent
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import ru.justd.fundaassignment.app.presenter.BasePresenter

abstract class BaseActivity<out P : BasePresenter<V>, V> : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        attachPresenter()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        attachPresenter()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        detachPresenter()
    }

    @CallSuper
    open fun attachPresenter() = getPresenter().attachView(getView())

    @CallSuper
    open fun detachPresenter() = getPresenter().detachView()

    abstract fun getView(): V

    abstract fun getPresenter(): P

}