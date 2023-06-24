package com.radhsyn83.themoviedb.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.data.models.ReviewsModel
import com.radhsyn83.themoviedb.data.models.TrailersModel
import com.radhsyn83.themoviedb.data.response.RemoteDataSource
import com.radhsyn83.themoviedb.vo.Resource
import javax.inject.Inject

@SuppressLint("CheckResult")
class DataSourcesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DataSource {

    @SuppressLint("CheckResult")
    override  fun genres(): LiveData<Resource<GenresModel>> {
    val res = MutableLiveData<Resource<GenresModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.genres()
            .subscribe({
                res.value = if (it.success == true)
                    Resource.success(it)
                else
                    Resource.error(it.statusMessage)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }

    override fun movies(id: Int, page: Int): LiveData<Resource<MoviesModel>> {
        val res = MutableLiveData<Resource<MoviesModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.movies(id, page)
            .subscribe({
                res.value = if (it.success == true)
                    Resource.success(it)
                else
                    Resource.error(it.statusMessage)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }

    override fun trailers(id: Int): LiveData<Resource<TrailersModel>> {
        val res = MutableLiveData<Resource<TrailersModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.trailers(id)
            .subscribe({
                res.value = if (it.success == true)
                    Resource.success(it)
                else
                    Resource.error(it.statusMessage)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }

    override fun detail(id: Int): LiveData<Resource<DetailModel>> {
        val res = MutableLiveData<Resource<DetailModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.detail(id)
            .subscribe({
                res.value = if (it.success == true)
                    Resource.success(it)
                else
                    Resource.error(it.statusMessage)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }

    override fun reviews(id: Int): LiveData<Resource<ReviewsModel>> {
        val res = MutableLiveData<Resource<ReviewsModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.reviews(id)
            .subscribe({
                res.value = if (it.success == true)
                    Resource.success(it)
                else
                    Resource.error(it.statusMessage)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }
}