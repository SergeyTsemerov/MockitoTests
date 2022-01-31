package com.sergeytsemerov.mockitotests.presenter.search

import com.sergeytsemerov.mockitotests.model.SearchResponse
import com.sergeytsemerov.mockitotests.presenter.RepositoryContract
import com.sergeytsemerov.mockitotests.presenter.SchedulerProvider
import com.sergeytsemerov.mockitotests.repository.RepositoryCallback
import com.sergeytsemerov.mockitotests.view.ViewContract
import com.sergeytsemerov.mockitotests.view.search.ViewSearchContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: RepositoryContract,
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : PresenterSearchContract, RepositoryCallback {

    private var view: ViewContract? = null
    fun getView() = view

    override fun onAttach(view: ViewContract) {
        this.view = view
    }

    override fun searchGitHub(searchQuery: String) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe { viewContract.displayLoading(true) }
                .doOnTerminate { viewContract.displayLoading(false) }
                .subscribeWith(object : DisposableObserver<SearchResponse>() {
                    override fun onNext(searchResponse: SearchResponse) {
                        val searchResults = searchResponse.searchResults
                        val totalCount = searchResponse.totalCount
                        if (searchResults != null && totalCount != null) {
                            viewContract.displaySearchResults(
                                searchResults, totalCount
                            )
                        } else {
                            viewContract.displayError(ERROR_RESULTS_NULL)
                        }
                    }

                    override fun onError(e: Throwable) {
                        viewContract.displayError(e.message ?: RESPONSE_NULL)
                    }

                    override fun onComplete() {}

                })
        )
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError(ERROR_RESULTS_NULL)
            }
        } else {
            viewContract.displayError(RESPONSE_NULL)
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }

    override fun onDetach() {
        view = null
    }

    companion object {
        private const val ERROR_RESULTS_NULL = "Search results or total count are null"
        private const val RESPONSE_NULL = "Response is null or unsuccessful"
    }
}