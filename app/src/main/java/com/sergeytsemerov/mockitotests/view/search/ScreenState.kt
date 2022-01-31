package com.sergeytsemerov.mockitotests.view.search

import com.sergeytsemerov.mockitotests.model.SearchResponse

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val searchResponse: SearchResponse) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}