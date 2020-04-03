package com.example.chucknorrisfacts.data.rest

import com.example.chucknorrisfacts.data.model.Fact
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChuckNorrisApi {
    @GET("random")
    fun randomJoke() : Observable<Fact>

    @GET("categories")
    fun categories() : Observable<List<String>>

    @GET("random")
    fun randomJokeByCategory(@Query("category") category: String): Observable<Fact>
}