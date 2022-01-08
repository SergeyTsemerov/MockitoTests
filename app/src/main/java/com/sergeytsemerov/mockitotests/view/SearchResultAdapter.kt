package com.sergeytsemerov.mockitotests.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergeytsemerov.mockitotests.databinding.ListItemBinding
import com.sergeytsemerov.mockitotests.model.SearchResult
import com.sergeytsemerov.mockitotests.view.SearchResultAdapter.SearchResultViewHolder

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<SearchResult> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: SearchResult) {
            binding.repositoryName.text = searchResult.fullName
        }
    }
}