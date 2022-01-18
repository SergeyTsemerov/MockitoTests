package com.sergeytsemerov.mockitotests.presenter

import com.sergeytsemerov.mockitotests.view.ViewContract

internal interface PresenterContract {
    fun onAttach(view: ViewContract)
    fun onDetach()
}