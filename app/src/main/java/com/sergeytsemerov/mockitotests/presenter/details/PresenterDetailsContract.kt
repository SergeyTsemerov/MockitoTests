package com.sergeytsemerov.mockitotests.presenter.details

import com.sergeytsemerov.mockitotests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}