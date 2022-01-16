package com.sergeytsemerov.mockitotests.view.details

import com.sergeytsemerov.mockitotests.view.ViewContract

internal interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}