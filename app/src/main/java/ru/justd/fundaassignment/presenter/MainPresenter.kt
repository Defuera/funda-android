package ru.justd.fundaassignment.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.fundaassignment.model.ApiResponse
import ru.justd.fundaassignment.model.RealtyObject
import ru.justd.fundaassignment.model.RealtyObjectsRepository
import ru.justd.fundaassignment.model.RealtyObjectsRepository.Companion.INITIAL_PAGE
import ru.justd.fundaassignment.view.MainView
import rx.Single
import rx.functions.Action1
import java.util.*
import javax.inject.Inject

/**
 * Created by defuera on 01/02/2017.
 */
class MainPresenter @Inject constructor(
        val repository: RealtyObjectsRepository
) : BasePresenter<MainView>() {

    companion object {
        const val AGENTS_TO_PRINT = 10
        const val DEBUG_MODE_PAGE_COUNT = 3
    }

    val objectsToCount = HashMap<RealtyObject, Int>()
    var pageLimit: Int = 0

    override fun onViewAttached() {}

    fun loadRealty(debugMode: Boolean) {
        pageLimit = if (debugMode) DEBUG_MODE_PAGE_COUNT else 0
        view().showLoading()
        loadObjects(INITIAL_PAGE, { page -> repository.loadObjects(page) })
    }

    fun loadGardens(debugMode: Boolean) {
        pageLimit = if (debugMode) DEBUG_MODE_PAGE_COUNT else 0
        view().showLoading()
        loadObjects(INITIAL_PAGE, { page -> repository.loadObjectsWithGarden(page) })
    }

    private fun loadObjects(page: Int, single: (x: Int) -> Single<ApiResponse<RealtyObject>>) {

        subscribe(
                single(page),
                Action1 {
                    response ->

                    val pagingInfo = response.pagingInfo
                    val currentPage = pagingInfo.currentPage
                    val lastPage = pagingInfo.lastPage

                    if (currentPage == INITIAL_PAGE) {
                        print("(total pages: $lastPage) ")
                    }

                    if (shouldProceedLoading(pagingInfo)) {
                        //print progress
                        print(".")

                        //Count agents realty objects
                        response.objects.forEach {
                            realty ->
                            val objectsCount = objectsToCount[realty]
                            if (objectsCount == null) {
                                objectsToCount.put(realty, 1)
                            } else {
                                objectsToCount.put(realty, objectsCount + 1)
                            }
                        }

                        //load next page
                        loadObjects(currentPage + 1, single)
                    } else {
                        //last page loaded, so print the result
                        view().showData(findTopAgents(AGENTS_TO_PRINT, objectsToCount))

                    }

                },
                Action1 { t -> view().showError(t.message) }
        )
    }

    fun shouldProceedLoading(pagingInfo: ApiResponse.PagingInfo): Boolean {
        val currentPage = pagingInfo.currentPage
        if (pageLimit != 0) {
            return currentPage < pageLimit
        } else {
            return currentPage < pagingInfo.lastPage
        }
    }

    fun findTopAgents(count: Int, unsortedMap: HashMap<RealtyObject, Int>): List<RealtyObject> {
        val list = LinkedList(unsortedMap.entries)
        Collections.sort(list) { o1, o2 -> o2.value.compareTo(o1.value) }

        val topList = list.subList(0, Math.min(count - 1, list.lastIndex))
        return topList.map { it.key }
    }
}