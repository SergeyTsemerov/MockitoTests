package com.sergeytsemerov.mockitotests.repository

import com.sergeytsemerov.mockitotests.presenter.RepositoryContract

class GetRepo {

    fun execute(create: GitHubApi? = null): RepositoryContract {
        if (create == null) {
            throw IllegalArgumentException(EXCEPTION)
        }
        return GitHubRepository(create)
    }

    companion object {
        private const val EXCEPTION = "Illegal Argument Exception"
    }
}