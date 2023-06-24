package com.radhsyn83.themoviedb.data.response

import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.data.models.ReviewsModel
import com.radhsyn83.themoviedb.data.models.TrailersModel
import com.radhsyn83.themoviedb.net.ApiServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
                       private val api: ApiServices) {

    fun genres(): Observable<GenresModel> =
        api.genres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun movies(id: Int, page: Int): Observable<MoviesModel> =
        api.movies(id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun trailers(id: Int): Observable<TrailersModel> =
        api.trailers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun detail(id: Int): Observable<DetailModel> =
        api.detail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun reviews(id: Int): Observable<ReviewsModel> =
        api.reviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}