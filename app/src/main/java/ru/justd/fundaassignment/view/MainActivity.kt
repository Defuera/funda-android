package ru.justd.fundaassignment.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import ru.justd.arkitec.view.BaseActivity
import ru.justd.fundaassignment.FundaApplication
import ru.justd.fundaassignment.R
import ru.justd.fundaassignment.model.RealtyObject
import ru.justd.fundaassignment.presenter.MainPresenter
import ru.justd.library.ProgressDialogFragment
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

        radio.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.agent -> presenter.loadRealty(debug.isChecked)
                R.id.garden -> presenter.loadGardens(debug.isChecked)
            }
        }
    }

    override fun showLoading() {
        ProgressDialogFragment.Builder(supportFragmentManager).create()
    }

    override fun showData(items: List<RealtyObject>) {
        ProgressDialogFragment.dismiss(supportFragmentManager)
        recycler.visibility = View.VISIBLE
        Toast.makeText(this, "first item: ${items[0].agentName}", Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: CharSequence?) {
        if (message != null) {
            Snackbar.make(recycler, message, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(recycler, R.string.unknown_error, Snackbar.LENGTH_SHORT).show()
        }
    }

}
