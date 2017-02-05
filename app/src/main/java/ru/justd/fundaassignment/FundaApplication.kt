package ru.justd.fundaassignment

import android.app.Application

/**
 * Created by defuera on 05/02/2017.
 */
class FundaApplication : Application(){

    companion object {
        lateinit var component: FundaComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerFundaComponent
                .builder()
                .fundaModule(FundaModule())
                .build()
    }
}