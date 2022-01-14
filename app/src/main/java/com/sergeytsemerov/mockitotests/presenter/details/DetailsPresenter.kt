package com.sergeytsemerov.mockitotests.presenter.details

import com.sergeytsemerov.mockitotests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract
) : PresenterDetailsContract {
}