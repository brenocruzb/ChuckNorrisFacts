package com.example.chucknorrisfacts.data.rest

import com.example.chucknorrisfacts.base.BaseRepository
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.data.model.Fact
import io.reactivex.Observable
import java.util.*

class ChuckNorrisRepository(private val chuckNorrisApi: ChuckNorrisApi): BaseRepository(){
    fun randomFact(): Observable<Fact> {
        return chuckNorrisApi.randomJoke().compose(applyObservableAsync())
    }

    fun categories(): Observable<List<Category>> {
        return chuckNorrisApi.categories()
            .map { t -> t.map { y -> Category(y, true, Date()) } }
        .compose(applyObservableAsync())
    }

    fun randomFactByCategory(category: String): Observable<Fact> {
        return chuckNorrisApi.randomJokeByCategory(category).compose(applyObservableAsync())
    }
}