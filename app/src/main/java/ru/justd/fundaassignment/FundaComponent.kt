package ru.justd.fundaassignment

import dagger.Component
import ru.justd.fundaassignment.view.MainActivity
import javax.inject.Singleton

/**
 * Created by defuera on 05/02/2017.
 */
@Singleton
@Component(modules = arrayOf(FundaModule::class))
interface FundaComponent {
    fun inject(mainActivity: MainActivity)
}
