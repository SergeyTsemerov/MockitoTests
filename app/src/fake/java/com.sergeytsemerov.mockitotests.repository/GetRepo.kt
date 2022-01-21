package com.sergeytsemerov.mockitotests.repository

import com.sergeytsemerov.mockitotests.presenter.RepositoryContract

class GetRepo {

    fun execute(create: GitHubApi? = null): RepositoryContract = FakeGitHubRepository()
}