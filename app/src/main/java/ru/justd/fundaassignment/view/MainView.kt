package ru.justd.fundaassignment.view

import ru.justd.fundaassignment.model.RealtyObject

/**
 * Created by defuera on 01/02/2017.
 */
interface MainView {

    fun showLoading()

    fun showData(items: List<RealtyObject>)

    fun showError(message: CharSequence?)

}