package com.radhsyn83.themoviedb.domain.repository

import com.radhsyn83.themoviedb.data.remote.dto.DetailDTO
import com.radhsyn83.themoviedb.data.remote.dto.GenresDTO
import com.radhsyn83.themoviedb.data.remote.dto.MoviesDTO
import com.radhsyn83.themoviedb.data.remote.dto.ReviewsDTO
import com.radhsyn83.themoviedb.data.remote.dto.TrailersDTO

interface MovieRepository {
    suspend fun genres(): GenresDTO

    suspend fun movies(id: Int, page: Int): MoviesDTO

    suspend fun trailers(id: Int): TrailersDTO

    suspend fun detail(id: Int): DetailDTO

    suspend fun reviews(id: Int, page: Int): ReviewsDTO
}