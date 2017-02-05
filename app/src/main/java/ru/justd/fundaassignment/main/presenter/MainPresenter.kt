package ru.justd.fundaassignment.main.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.fundaassignment.main.view.MainView

/**
 * Created by defuera on 01/02/2017.
 */
class MainPresenter : BasePresenter<MainView>() {

//    lateinit var fundaRepository: FundaRepository

    override fun onViewAttached() {
        fundaRepository
    }

}