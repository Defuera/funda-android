package ru.justd.fundaassignment.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.m039.el_adapter.ListItemAdapter
import ru.justd.arkitec.view.BaseActivity
import ru.justd.fundaassignment.BuildConfig
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

    @Inject
    lateinit var presenter: MainPresenter

    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView

    @BindView(R.id.agent)
    lateinit var agent: Button

    @BindView(R.id.agent_with_garden)
    lateinit var agentWithGarden: Button

    @BindView(R.id.debug_checkbox)
    lateinit var debug: CheckBox

    @BindView(R.id.progress)
    lateinit var progress: TextView

    private var snack: Snackbar? = null
    val adapter = ListItemAdapter()

    override fun presenter() = presenter
    override fun view() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        FundaApplication.component.inject(this)

        adapter.addViewCreator(
                RealtyObject::class.java,
                { parent -> RealtyObjectWidget(parent.context) }
        )
                .addViewBinder(RealtyObjectWidget::bind)

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        agent.setOnClickListener {
            presenter.loadRealty(debug.isChecked)
        }
        agentWithGarden.setOnClickListener {
            presenter.loadGardens(debug.isChecked)
        }

        debug.isChecked = BuildConfig.DEBUG
    }


    //region MainView

    override fun showData(items: List<RealtyObject>) {
        ProgressDialogFragment.dismiss(supportFragmentManager)
        progress.visibility = View.GONE
        snack?.dismiss()

        recycler.adapter = adapter
        adapter.addItems(items)
    }

    override fun publishProgress(currentPage: Int, totalPages: Int) {
        progress.text = getString(R.string.progress, currentPage, totalPages)
    }

    override fun showLoading() {
        snack?.dismiss()
        adapter.removeAllItems()
        progress.text = null
        progress.visibility = View.VISIBLE
        ProgressDialogFragment.Builder(supportFragmentManager).create()
    }

    override fun showError(message: CharSequence?) {
        ProgressDialogFragment.dismiss(supportFragmentManager)
        progress.visibility = View.GONE

        if (message != null) {
            snack = Snackbar.make(recycler, message, Snackbar.LENGTH_INDEFINITE)
        } else {
            snack = Snackbar.make(recycler, R.string.unknown_error, Snackbar.LENGTH_INDEFINITE)
        }

        snack?.show()
    }

    //endregion

}
