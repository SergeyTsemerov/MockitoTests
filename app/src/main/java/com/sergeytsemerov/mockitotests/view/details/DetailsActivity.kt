package com.sergeytsemerov.mockitotests.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergeytsemerov.mockitotests.R
import com.sergeytsemerov.mockitotests.databinding.ActivityDetailsBinding
import com.sergeytsemerov.mockitotests.presenter.details.DetailsPresenter
import com.sergeytsemerov.mockitotests.presenter.details.PresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportFragmentManager.beginTransaction()
            .add(
                R.id.detailsFragmentContainer,
                DetailsFragment.newInstance(intent.getIntExtra(TOTAL_COUNT_EXTRA, DEFAULT_VALUE))
            )
            .commitAllowingStateLoss()
    }

    companion object {
        private const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"
        const val DEFAULT_VALUE = 0

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }
}