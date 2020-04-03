package com.example.chucknorrisfacts.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.base.BaseActivity
import com.example.chucknorrisfacts.data.model.Fact
import com.example.chucknorrisfacts.ui.search.SearchActivity
import com.example.chucknorrisfacts.util.showIfTrue
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    private val mainAdapter: MainAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_facts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mainAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            startActivityForResult(Intent(this, SearchActivity::class.java), codeRequest)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeRequest && resultCode == Activity.RESULT_OK) {
            val fact = data?.getSerializableExtra(categoryResponse) as Fact
            mainViewModel.addFact(fact)
        }
    }

    override fun observeComponents() {
        mainViewModel.facts.observe(this, Observer { insertFacts(it) })
    }

    private fun insertFacts(facts: List<Fact>) {
        mainAdapter.insertFacts(facts)
        val hasItems = mainAdapter.itemCount > 0

        main_container.showIfTrue(!hasItems)
        recycler_facts.showIfTrue(hasItems)
    }
}
