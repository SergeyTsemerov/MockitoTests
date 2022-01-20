package com.sergeytsemerov.mockitotests.presenter

import com.sergeytsemerov.mockitotests.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}