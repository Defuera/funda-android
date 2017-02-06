package ru.justd.fundaassignment.view

import ru.justd.fundaassignment.model.RealtyObject

/**
 * Created by defuera on 01/02/2017.
 */
interface MainView {

    fun showData(items: List<RealtyObject>)

    fun publishProgress(currentPage: Int, totalPages: Int)

    fun showLoading()

    fun showError(message: CharSequence?)

}