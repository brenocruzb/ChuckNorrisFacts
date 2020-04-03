package com.example.chucknorrisfacts.ui.main

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.data.model.Fact
import com.example.chucknorrisfacts.util.inflate
import kotlinx.android.synthetic.main.recycler_fact.view.*
import java.util.Locale

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private val locale = Locale("pt", "BR")
    private val factList = arrayListOf<Fact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.recycler_fact))

    override fun getItemCount(): Int = factList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(factList[position])

    fun insertFacts(facts: List<Fact>){
        factList.clear()
        factList.addAll(facts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bind(data: Fact) = with(view){
            tv_fact.text = data.value
            formatTextSize(tv_fact)
            tv_category.text = formatCategory(context, data.categories)
            iv_share.setOnClickListener { shareText(context, data.url) }
        }

        private fun formatTextSize(textView: TextView) {
            val textSize = textView.text.length
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                if(textSize < 80)
                    20.toFloat()
                else
                    14.toFloat()
            )
        }

        private fun formatCategory(context: Context, categories: List<String>): String {
            return when {
                categories.isEmpty() -> context.getString(R.string.uncategorized)
                categories.size == 1 -> categories[0]
                else -> categories.toList().toString()
            }.toUpperCase(locale)
        }

        private fun shareText(context: Context, text: String) {
            val body = "${context.getString(R.string.share_message)}\n\n$text"

            val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, body)
            }
            context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share)))
        }
    }
}