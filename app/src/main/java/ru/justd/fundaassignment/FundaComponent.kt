package ru.justd.fundaassignment

import dagger.Component
import ru.justd.fundaassignment.view.MainActivity

/**
 * Created by defuera on 05/02/2017.
 */
@Component(modules = arrayOf(FundaModule::class))
interface FundaComponent {
    fun inject(mainActivity: MainActivity)
}
