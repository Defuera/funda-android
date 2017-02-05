package ru.justd.fundaassignment.view

import android.os.Bundle
import ru.justd.fundaassignment.FundaApplication
import ru.justd.arkitec.view.BaseActivity
import ru.justd.fundaassignment.R
import ru.justd.fundaassignment.presenter.MainPresenter
import javax.inject.Inject

/**
 * Determine which makelaar's in Amsterdam have the most object listed for sale.
 * Make a table of the top 10. Then do the same thing but only for objects with a tuin which are
 * listed for sale. For the assignment you may write a program in the language of your choice and
 * you may use any libraries that you find useful.
 */
class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    @Inject lateinit var presenter: MainPresenter

    override fun presenter() = presenter

    override fun view() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FundaApplication.component.inject(this)

        setContentView(R.layout.activity_main)
    }


}
