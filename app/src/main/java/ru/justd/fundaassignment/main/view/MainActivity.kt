package ru.justd.fundaassignment.main.view

import android.os.Bundle
import ru.justd.fundaassignment.app.view.BaseActivity
import ru.justd.fundaassignment.R
import ru.justd.fundaassignment.main.presenter.MainPresenter

/**
 * Determine which makelaar's in Amsterdam have the most object listed for sale.
 * Make a table of the top 10. Then do the same thing but only for objects with a tuin which are
 * listed for sale. For the assignment you may write a program in the language of your choice and
 * you may use any libraries that you find useful.
 */
class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    private lateinit var presenter: MainPresenter

    override fun getPresenter() = presenter

    override fun getView() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = MainPresenter() //this basically should be injected with dagger

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
