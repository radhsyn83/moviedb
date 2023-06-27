package com.radhsyn83.themoviedb.data.repository

import com.radhsyn83.themoviedb.data.remote.MovieDBApi
import com.radhsyn83.themoviedb.data.remote.dto.DetailDTO
import com.radhsyn83.themoviedb.data.remote.dto.GenresDTO
import com.radhsyn83.themoviedb.data.remote.dto.MoviesDTO
import com.radhsyn83.themoviedb.data.remote.dto.ReviewsDTO
import com.radhsyn83.themoviedb.data.remote.dto.TrailersDTO
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieDBApi,
) : MovieRepository {

    override suspend  fun genres(): GenresDTO = api.genres()

    override suspend fun movies(id: Int, page: Int): MoviesDTO = api.movies(id, page)

    override suspend  fun trailers(id: Int): TrailersDTO = api.trailers(id)

    override suspend  fun detail(id: Int): DetailDTO = api.detail(id)

    override suspend  fun reviews(id: Int, page: Int): ReviewsDTO = api.reviews(id, page)
}