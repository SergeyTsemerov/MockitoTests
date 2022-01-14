package com.sergeytsemerov.mockitotests.presenter.search

import com.sergeytsemerov.mockitotests.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
}