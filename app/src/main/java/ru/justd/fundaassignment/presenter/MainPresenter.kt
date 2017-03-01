package ru.justd.fundaassignment.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.fundaassignment.model.ApiResponse
import ru.justd.fundaassignment.model.Makelaar
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

    val objectsToCount = HashMap<Makelaar, Int>()
    var debugMode = false

    override fun onViewAttached() {}

    fun loadRealty(debugMode: Boolean) {
        prepareStartLoading(debugMode, { page -> repository.loadObjects(page) })
    }

    fun loadGardens(debugMode: Boolean) {
        prepareStartLoading(debugMode, { page -> repository.loadObjectsWithGarden(page) })
    }

    private fun prepareStartLoading(debugMode: Boolean, single: (x: Int) -> Single<ApiResponse<Makelaar>>) {
        this.debugMode = debugMode
        view().showLoading()
        loadObjects(INITIAL_PAGE, single)
    }

    private fun loadObjects(page: Int, single: (x: Int) -> Single<ApiResponse<Makelaar>>) {

        subscribe(
                single(page),
                Action1 {
                    response ->

                    val pagingInfo = response.pagingInfo
                    val currentPage = pagingInfo.currentPage

                    if (shouldProceedLoading(pagingInfo)) {

                        view().publishProgress(
                                currentPage,
                                if (debugMode) DEBUG_MODE_PAGE_COUNT else pagingInfo.lastPage
                        )

                        //Count agents realty objects
                        response.objects.forEach {
                            makelaar ->
                            val makelaarCount = objectsToCount[makelaar]
                            if (makelaarCount == null) {
                                objectsToCount.put(makelaar, 1)
                            } else {
                                objectsToCount.put(makelaar, makelaarCount + 1)
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

    /**
     * Checks whether it's last page, or debug limit is set
     */
    fun shouldProceedLoading(pagingInfo: ApiResponse.PagingInfo): Boolean {
        val currentPage = pagingInfo.currentPage
        if (debugMode) {
            return currentPage < DEBUG_MODE_PAGE_COUNT
        } else {
            return currentPage < pagingInfo.lastPage
        }
    }

    /**
     * @param count - number of objects to return
     * @return agents with most object listed for sale.
     */
    fun findTopAgents(count: Int, unsortedMap: HashMap<Makelaar, Int>): List<Makelaar> {
        val list = LinkedList(unsortedMap.entries)
        Collections.sort(list) { o1, o2 -> o2.value.compareTo(o1.value) }

        val topList = list.subList(0, Math.min(count, list.lastIndex))
        return topList.map { it.key }
    }
}