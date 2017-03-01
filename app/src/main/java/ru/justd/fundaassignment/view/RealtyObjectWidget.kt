package ru.justd.fundaassignment.view

import android.content.Context
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.widget_realty_object.view.*
import ru.justd.fundaassignment.R
import ru.justd.fundaassignment.model.Makelaar

/**
 * Created by defuera on 05/02/2017.
 */
class RealtyObjectWidget(context: Context) : LinearLayout(context) {

    init {
        inflate(context, R.layout.widget_realty_object, this)
        orientation = HORIZONTAL
    }

    fun bind(item: Makelaar) {
        name.text = item.agentName
    }

}