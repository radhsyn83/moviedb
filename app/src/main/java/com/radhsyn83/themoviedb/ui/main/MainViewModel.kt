package com.radhsyn83.themoviedb.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var error by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    var genres by mutableStateOf(listOf<GenresModel.Result>())
        private set

    init {
        load()
    }

    fun load() {
        dataSource.genres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GenresModel> {
                override fun onSubscribe(d: Disposable) {
                    loading = true
                    error = ""
                }

                override fun onNext(t: GenresModel) {
                    loading = false
                    isRefreshing = false
                    if (t.statusMessage.isNullOrEmpty()) {
                        genres = t.genres
                    }
                    else {
                        error = t.statusMessage.toString()
                    }
                }

                override fun onError(e: Throwable) {
                    loading = false
                    isRefreshing = false
                    error = e.message.toString()
                }

                override fun onComplete() {
                    loading = false
                    isRefreshing = false
                }
            })
    }
}