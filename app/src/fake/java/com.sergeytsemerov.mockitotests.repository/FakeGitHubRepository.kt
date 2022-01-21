package com.sergeytsemerov.mockitotests.repository

import com.sergeytsemerov.mockitotests.model.SearchResponse
import com.sergeytsemerov.mockitotests.presenter.RepositoryContract
import retrofit2.Response

internal class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}