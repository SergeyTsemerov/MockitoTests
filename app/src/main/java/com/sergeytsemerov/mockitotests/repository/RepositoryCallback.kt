package com.sergeytsemerov.mockitotests.repository

import com.sergeytsemerov.mockitotests.model.SearchResponse
import retrofit2.Response

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}