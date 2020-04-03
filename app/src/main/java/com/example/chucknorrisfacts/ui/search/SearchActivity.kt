package com.example.chucknorrisfacts.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.lujun.androidtagview.TagView
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.base.BaseActivity
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.data.model.Fact
import com.example.chucknorrisfacts.util.hideKeyboard
import com.example.chucknorrisfacts.util.showIfTrue
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity: BaseActivity(), TagView.OnTagClickListener {
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tag_container.setOnTagClickListener(this)
        et_search.setOnKeyListener { _, _, event ->
            return@setOnKeyListener if(
                (event.action == KeyEvent.ACTION_DOWN) &&
                (event.keyCode == KeyEvent.KEYCODE_ENTER)
            ){
                searchFact(et_search.text.toString())
                true
            }else false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return true
    }

    override fun observeComponents() {
        searchViewModel.apply {
            loading.observe(this@SearchActivity, Observer { showComponents(it) })
            suggestionCategories.observe(this@SearchActivity, Observer { loadCategories(it) })
            pastSearchCategories.observe(this@SearchActivity, Observer { loadAdapter(it) })
            fact.observe(this@SearchActivity, Observer { sendFact(it) })
            error.observe(this@SearchActivity, Observer { showMessage(it) })
        }
    }

    private fun sendFact(fact: Fact) {
        val intent = Intent().apply { putExtra(categoryResponse, fact) }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun loadCategories(categories: List<Category>) {
        val randomCategories:List<String> = categories.shuffled().take(8).map { t -> t.name }
        tag_container.tags = randomCategories
    }

    private fun showComponents(isLoading: Boolean) {
        progress_bar.showIfTrue(isLoading)
        main_container.showIfTrue(!isLoading)
    }

    private fun loadAdapter(searchCategories: List<Category>) {
        val searchAdapter: SearchAdapter by inject {
            parametersOf(
                searchCategories.reversed(),
                { search: String -> searchFact(search) }
            )
        }

        recycler_searches.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = searchAdapter
        }
    }

    override fun onSelectedTagDrag(position: Int, text: String?) {}

    override fun onTagLongClick(position: Int, text: String?) { searchFact(text) }

    override fun onTagClick(position: Int, text: String?) { searchFact(text) }

    override fun onTagCrossClick(position: Int) {}

    private fun searchFact(category: String?){
        hideKeyboard()
        showComponents(true)
        searchViewModel.savePastSearch(category)
        searchViewModel.getRandomFactByCategory(category)
    }
}