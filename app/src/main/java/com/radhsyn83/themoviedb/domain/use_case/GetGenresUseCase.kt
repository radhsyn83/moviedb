package com.radhsyn83.themoviedb.domain.use_case

import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.data.remote.dto.toGenres
import com.radhsyn83.themoviedb.domain.model.Genre
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        try {
            emit(Resource.Loading<List<Genre>>())
            val res = repository.genres().toGenres()
            emit(Resource.Success<List<Genre>>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Genre>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Genre>>("Couldn't reach server. Check your internet connection."))
        }
    }
}