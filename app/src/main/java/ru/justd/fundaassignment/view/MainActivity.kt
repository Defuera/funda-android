package ru.justd.fundaassignment.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.loader
import kotlinx.android.synthetic.main.activity_main.recycler
import ru.justd.fundaassignment.R

/**
 * Determine which makelaar's in Amsterdam have the most object listed for sale.
 * Make a table of the top 10. Then do the same thing but only for objects with a tuin which are
 * listed for sale. For the assignment you may write a program in the language of your choice and
 * you may use any libraries that you find useful.
 */
class MainActivity : AppCompatActivity() {

    lateinit var mainPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loader.visibility = VISIBLE
        recycler.visibility = INVISIBLE
    }

}
