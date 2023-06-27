package com.radhsyn83.themoviedb.data.remote


import com.radhsyn83.themoviedb.data.remote.dto.DetailDTO
import com.radhsyn83.themoviedb.data.remote.dto.GenresDTO
import com.radhsyn83.themoviedb.data.remote.dto.MoviesDTO
import com.radhsyn83.themoviedb.data.remote.dto.ReviewsDTO
import com.radhsyn83.themoviedb.data.remote.dto.TrailersDTO
import retrofit2.http.*

interface MovieDBApi {
    @GET("genre/movie/list")
    suspend fun genres(): GenresDTO

    @GET("discover/movie")
    suspend fun movies(
        @Query("with_genres") id: Int,
        @Query("page") page: Int,
    ):  MoviesDTO

    @GET("movie/{id}")
    suspend fun detail(
        @Path("id") id: Int,
    ): DetailDTO

    @GET("movie/{id}/reviews")
    suspend fun reviews(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): ReviewsDTO

    @GET("movie/{id}/trailers")
    suspend fun trailers(
        @Path("id") id: Int,
    ): TrailersDTO
}
