package com.sergeytsemerov.mockitotests.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sergeytsemerov.mockitotests.R
import com.sergeytsemerov.mockitotests.databinding.ActivityMainBinding
import com.sergeytsemerov.mockitotests.model.SearchResult
import com.sergeytsemerov.mockitotests.presenter.search.PresenterSearchContract
import com.sergeytsemerov.mockitotests.presenter.search.SearchPresenter
import com.sergeytsemerov.mockitotests.repository.GitHubApi
import com.sergeytsemerov.mockitotests.repository.GitHubRepository
import com.sergeytsemerov.mockitotests.view.details.DetailsActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private lateinit var binding: ActivityMainBinding
    private val adapter = SearchResultAdapter()
    private val presenter: PresenterSearchContract = SearchPresenter(this, createRepository())
    private var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(
                DetailsActivity.getIntent(this, totalCount)
            )
        }
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        this.totalCount = totalCount
        adapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}