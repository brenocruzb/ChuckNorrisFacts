package com.example.chucknorrisfacts.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chucknorrisfacts.base.BaseViewModel
import com.example.chucknorrisfacts.data.dao.CategoriesRepository
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.data.model.Fact
import com.example.chucknorrisfacts.data.rest.ChuckNorrisRepository
import com.example.chucknorrisfacts.util.customMessage
import java.util.Date

class SearchViewModel(
    private val chuckNorrisRepository: ChuckNorrisRepository,
    private val categoriesRepository: CategoriesRepository
): BaseViewModel(){

    private val mutableSuggestionCategories = MutableLiveData<List<Category>>()
    val suggestionCategories : LiveData<List<Category>> get() = mutableSuggestionCategories

    private val mutablePastSearchCategories = MutableLiveData<List<Category>>()
    val pastSearchCategories : LiveData<List<Category>> get() = mutablePastSearchCategories

    private val mutableFact = MutableLiveData<Fact>()
    val fact: LiveData<Fact> get() = mutableFact

    init {
        loadSuggestions()
        loadPastSearch()
    }

    private fun loadSuggestions() {
        val daoCategories = categoriesRepository.getAllSuggestions().take(1)
            .subscribe { categories ->
                if(categories.isNotEmpty()){
                    mutableSuggestionCategories.value = categories
                }else{
                    mutableLoading.value = true

                    val restCategories = chuckNorrisRepository.categories().subscribe(
                        { list ->
                            categoriesRepository.insertAll(list)
                            mutableSuggestionCategories.value = list
                        },
                        onError,
                        onComplete
                    )
                    compositeDisposable.add(restCategories)
                }
            }
        compositeDisposable.add(daoCategories)
    }

    private fun loadPastSearch() {
        val list = categoriesRepository.getAllPastSearchesByDate().subscribe{ categories ->
            mutablePastSearchCategories.value = categories
        }
        compositeDisposable.add(list)
    }

    fun savePastSearch(pastSearch: String?){
        val daoCategories = categoriesRepository.getAllPastSearchesByDate().
                subscribe { categories ->
                    if(!pastSearch.isNullOrEmpty()){
                        val actualCategory: Category? = categories.find { t ->
                            t.name == pastSearch
                        }

                        if(actualCategory != null){
                            actualCategory.date = Date()
                            categoriesRepository.update(actualCategory)
                        }else{
                            val category = Category(pastSearch, isSuggestion = false, date = Date())
                            categoriesRepository.insert(category)
                        }
                    }
                }
        compositeDisposable.add(daoCategories)
    }

    fun getRandomFactByCategory(category: String?) {
        mutableLoading.value = true
        val disposable = (
                if(!category.isNullOrEmpty())
                    chuckNorrisRepository.randomFactByCategory(category)
                else
                    chuckNorrisRepository.randomFact()
                )
            .subscribe(onNext(mutableFact), { e ->
                mutableError.value = e.customMessage()
                mutableLoading.value = false
                loadPastSearch()
            })

        compositeDisposable.add(disposable)
    }
}