package ru.justd.fundaassignment.view

import android.content.Context
import android.widget.TextView
import ru.justd.fundaassignment.model.RealtyObject

/**
 * Created by defuera on 05/02/2017.
 */
class RealtyObjectWidget(context: Context) : TextView(context) {

    fun bind(item: RealtyObject) {
        text = "${item.agentName}"
    }
}