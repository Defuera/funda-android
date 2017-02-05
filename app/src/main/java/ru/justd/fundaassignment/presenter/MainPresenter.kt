package ru.justd.fundaassignment.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.fundaassignment.model.RealtyObjectsRepository
import ru.justd.fundaassignment.view.MainView
import javax.inject.Inject

/**
 * Created by defuera on 01/02/2017.
 */
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    lateinit var repository: RealtyObjectsRepository

    override fun onViewAttached() {
    }

}