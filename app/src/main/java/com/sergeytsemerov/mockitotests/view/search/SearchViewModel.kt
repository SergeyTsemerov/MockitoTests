package com.sergeytsemerov.mockitotests.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergeytsemerov.mockitotests.model.SearchResponse
import com.sergeytsemerov.mockitotests.presenter.RepositoryContract
import com.sergeytsemerov.mockitotests.presenter.SchedulerProvider
import com.sergeytsemerov.mockitotests.presenter.search.SearchSchedulerProvider
import com.sergeytsemerov.mockitotests.repository.GitHubApi
import com.sergeytsemerov.mockitotests.repository.GitHubRepository
import com.sergeytsemerov.mockitotests.view.search.MainActivity.Companion.BASE_URL
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class SearchViewModel(
    private val repository: RepositoryContract = GitHubRepository(
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubApi::class.java)
    ),
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData

    fun subscribeToLiveData() = liveData

    fun searchGitHub(searchQuery: String) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe { _liveData.value = ScreenState.Loading }
                .subscribeWith(object : DisposableObserver<SearchResponse>() {
                    override fun onNext(searchResponse: SearchResponse) {
                        val searchResults = searchResponse.searchResults
                        val totalCount = searchResponse.totalCount
                        if (searchResults != null && totalCount != null) {
                            _liveData.value = ScreenState.Working(searchResponse)
                        } else {
                            _liveData.value = ScreenState.Error(Throwable(ERROR_RESULTS_NULL))
                        }
                    }

                    override fun onError(e: Throwable) {
                        _liveData.value = ScreenState.Error(Throwable(e.message ?: RESPONSE_NULL))
                    }

                    override fun onComplete() {}
                })
        )
    }

    companion object {
        private const val ERROR_RESULTS_NULL = "Search results or total count are null"
        private const val RESPONSE_NULL = "Response is null or unsuccessful"
    }
}

