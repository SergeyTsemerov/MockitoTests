package com.sergeytsemerov.mockitotests.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sergeytsemerov.mockitotests.presenter.RepositoryContract
import com.sergeytsemerov.mockitotests.repository.GitHubApi
import com.sergeytsemerov.mockitotests.repository.GitHubRepository
import com.sergeytsemerov.mockitotests.view.search.MainActivity.Companion.BASE_URL
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchViewModel(
    private val repository: RepositoryContract = GitHubRepository(
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubApi::class.java)
    )
) : ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    fun subscribeToLiveData() = liveData

    fun searchGitHub(searchQuery: String) {
        _liveData.value = ScreenState.Loading
        viewModelCoroutineScope.launch {
            val searchResponse = repository.searchGithubAsync(searchQuery)
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
                _liveData.value = ScreenState.Working(searchResponse)
            } else {
                _liveData.value = ScreenState.Error(Throwable(ERROR_RESULTS_NULL))
            }
        }
    }

    private fun handleError(error: Throwable) {
        _liveData.value = ScreenState.Error(Throwable(error.message ?: RESPONSE_NULL))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    companion object {
        private const val ERROR_RESULTS_NULL = "Search results or total count are null"
        private const val RESPONSE_NULL = "Response is null or unsuccessful"
    }
}

