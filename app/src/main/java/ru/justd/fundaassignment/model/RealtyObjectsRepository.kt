package ru.justd.fundaassignment.model

import ru.justd.fundaassignment.BuildConfig
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by defuera on 05/02/2017.
 */
@Singleton
class RealtyObjectsRepository @Inject constructor(val apiService: ApiService){

    val INITIAL_PAGE = 0
    val TYPE_PURCHASE = "koop"
    val SEARCH_QUERY_GARDEN = "/amsterdam/tuin/"
    val SEARCH_QUERY_REALTY = "/amsterdam/"
    val PER_PAGE = 25

    fun loadObjects(page: Int): Single<ApiResponse<RealtyObject>> {
        return apiService
                .fetch(BuildConfig.API_KEY, TYPE_PURCHASE, SEARCH_QUERY_REALTY, page, PER_PAGE)
    }

    fun loadObjectsWithGarden(page: Int): Single<ApiResponse<RealtyObject>> {
        return apiService
                .fetch(BuildConfig.API_KEY, TYPE_PURCHASE, SEARCH_QUERY_GARDEN, page, PER_PAGE)
    }

}