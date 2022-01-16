package com.sergeytsemerov.mockitotests

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.sergeytsemerov.mockitotests.presenter.details.DetailsPresenter
import com.sergeytsemerov.mockitotests.view.details.ViewDetailsContract
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    private var count: Int = 0

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = DetailsPresenter(viewContract, count)
    }

    @Test
    fun onAttach_NotNull_Test() {
        presenter.onAttach(mock())
        assertNotNull(presenter.getView())
    }

    @Test
    fun setCounter_Test() {
        presenter.setCounter(count)
        assertEquals(count, 0)
    }

    @Test
    fun onIncrement_Test() {
        presenter.onIncrement()
        verify(viewContract, times(1)).setCount(1)
    }

    @Test
    fun onDecrement_Test() {
        presenter.onDecrement()
        verify(viewContract, times(1)).setCount(-1)
    }

    @Test
    fun onDetach_Test() {
        presenter.onAttach(mock())
        presenter.onDetach()
        assertNull(presenter.getView())
    }
}