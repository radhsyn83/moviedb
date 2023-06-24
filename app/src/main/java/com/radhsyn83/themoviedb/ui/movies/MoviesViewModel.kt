package com.radhsyn83.themoviedb.ui.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var page by mutableStateOf(1)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    var canLoadMore by mutableStateOf(true)
        private set

    var error by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var movies by mutableStateOf(listOf<MoviesModel.Result>())
        private set

    fun load(id: Int, isLoadMore: Boolean = false) {
        if (!isLoadMore) {
            page = 1
            canLoadMore = true
        }
        dataSource.movies(id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MoviesModel> {
                override fun onSubscribe(d: Disposable) {
                   if (!isLoadMore) {
                       loading = true
                   }
                }

                override fun onNext(t: MoviesModel) {
                    loading = false
                    isRefreshing = false
                    if (t.statusMessage.isNullOrEmpty()) {
                        if (t.totalPages != page ) {
                            page++
                        } else {
                            canLoadMore = false
                        }
                        movies = if (isLoadMore) {
                            movies + (t.results ?: listOf())
                        } else {
                            t.results ?: listOf()
                        }
                    } else {
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

    fun setErrorMessage(msg: String) {
        error = msg
    }
}