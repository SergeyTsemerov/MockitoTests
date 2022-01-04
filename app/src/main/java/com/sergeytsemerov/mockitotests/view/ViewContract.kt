package com.sergeytsemerov.mockitotests.view

import com.sergeytsemerov.mockitotests.model.SearchResult


internal interface ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}