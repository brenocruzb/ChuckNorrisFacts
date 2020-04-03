package com.example.chucknorrisfacts.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.util.inflate
import kotlinx.android.synthetic.main.recycler_search.view.*

class SearchAdapter(
    private val pastSearchesList: List<Category>,
    private val listener:(String) -> Unit
): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.recycler_search))

    override fun getItemCount(): Int = pastSearchesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(pastSearchesList[position], listener)

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bind(category: Category, listener: (String) -> Unit) = with(view){
            tv_search.text = category.name
            setOnClickListener { listener(category.name) }
        }
    }
}