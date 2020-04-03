package com.example.chucknorrisfacts.di

import androidx.room.Room
import com.example.chucknorrisfacts.BuildConfig
import com.example.chucknorrisfacts.data.dao.AppDatabase
import com.example.chucknorrisfacts.data.dao.CategoriesRepository
import com.example.chucknorrisfacts.data.local.PreferenceManager
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.data.rest.ChuckNorrisApi
import com.example.chucknorrisfacts.data.rest.ChuckNorrisRepository
import com.example.chucknorrisfacts.ui.main.MainAdapter
import com.example.chucknorrisfacts.ui.main.MainViewModel
import com.example.chucknorrisfacts.ui.search.SearchAdapter
import com.example.chucknorrisfacts.ui.search.SearchViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val prefModule = module {
    single { PreferenceManager(androidContext()) }
}

val categoryDaoModule = module {
    single { Room.databaseBuilder(
        androidContext(),
        AppDatabase::class.java,
        "chuck_norris-database")
        .build()
    }
    single { get<AppDatabase>().categoryDao() }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideForecastApi(get()) }
    single { provideRetrofit(get()) }
}

val repositoryModule = module {
    factory { ChuckNorrisRepository(get()) }
    factory { CategoriesRepository(get()) }
}

val viewModelModule = module {
    factory { MainViewModel() }
    factory { SearchViewModel(get(), get()) }
}

val adapterModule = module {
    factory { MainAdapter() }
    factory {
            (pastSearchesList: List<Category>, listener: (String) -> Unit) ->
        SearchAdapter(pastSearchesList, listener)
    }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    val timeOut: Long = 15
    return OkHttpClient().newBuilder()
        .readTimeout(timeOut, TimeUnit.SECONDS)
        .connectTimeout(timeOut, TimeUnit.SECONDS)
        .build()
}

private fun provideForecastApi(retrofit: Retrofit): ChuckNorrisApi =
    retrofit.create(ChuckNorrisApi::class.java)