package com.radhsyn83.themoviedb.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var error by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var detail by mutableStateOf<DetailModel?>(null)
        private set

    fun load(id: Int) {
        dataSource.detail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DetailModel> {
                override fun onSubscribe(d: Disposable) {
                    loading = true
                    error = ""
                }

                override fun onNext(t: DetailModel) {
                    loading = false
                    if (t.statusMessage.isNullOrEmpty()) {
                        detail = t
                    }
                    else {
                        error = t.statusMessage.toString()
                    }
                }

                override fun onError(e: Throwable) {
                    loading = false
                    error = e.message.toString()
                }

                override fun onComplete() {
                    loading = false
                }
            })
    }
}