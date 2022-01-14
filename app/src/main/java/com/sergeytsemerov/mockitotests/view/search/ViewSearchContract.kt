package com.sergeytsemerov.mockitotests.view.search

import com.sergeytsemerov.mockitotests.model.SearchResult
import com.sergeytsemerov.mockitotests.view.ViewContract

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}