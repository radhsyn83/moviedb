package com.radhsyn83.themoviedb.domain.use_case

import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.data.remote.dto.toMovies
import com.radhsyn83.themoviedb.domain.model.Movie
import com.radhsyn83.themoviedb.domain.model.Page
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int, page: Int): Flow<Resource<Page<List<Movie>>>> = flow {
        try {
            emit(Resource.Loading<Page<List<Movie>>>())
            val res = repository.movies(id, page).toMovies()
            emit(Resource.Success<Page<List<Movie>>>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<Page<List<Movie>>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<Page<List<Movie>>>("Couldn't reach server. Check your internet connection."))
        }
    }
}