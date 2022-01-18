package com.sergeytsemerov.mockitotests.presenter.details

import com.sergeytsemerov.mockitotests.view.ViewContract
import com.sergeytsemerov.mockitotests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract,
    private var count: Int = 0
) : PresenterDetailsContract {

    private var view: ViewContract? = null
    fun getView() = view

    override fun onAttach(view: ViewContract) {
        this.view = view
    }

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }

    override fun onDetach() {
        view = null
    }
}