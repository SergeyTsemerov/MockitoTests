package com.sergeytsemerov.mockitotests.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sergeytsemerov.mockitotests.R
import com.sergeytsemerov.mockitotests.databinding.FragmentDetailsBinding
import com.sergeytsemerov.mockitotests.presenter.details.DetailsPresenter
import com.sergeytsemerov.mockitotests.presenter.details.PresenterDetailsContract
import java.util.*

class DetailsFragment : Fragment(), ViewDetailsContract {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val presenter: PresenterDetailsContract = DetailsPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        setUI()
    }

    private fun setUI() {
        arguments?.let {
            val count = it.getInt(TOTAL_COUNT_EXTRA, DEFAULT_VALUE)
            presenter.setCounter(count)
            setCountText(count)
        }
        binding.decrementButton.setOnClickListener { presenter.onDecrement() }
        binding.incrementButton.setOnClickListener { presenter.onIncrement() }
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        binding.totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDetach()
    }

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"
        const val DEFAULT_VALUE = 0

        @JvmStatic
        fun newInstance(counter: Int) =
            DetailsFragment().apply { arguments = bundleOf(TOTAL_COUNT_EXTRA to counter) }
    }
}