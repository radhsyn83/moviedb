package com.radhsyn83.themoviedb.domain.use_case

import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.data.remote.dto.toReviews
import com.radhsyn83.themoviedb.domain.model.Review
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int, page: Int): Flow<Resource<List<Review>>> = flow {
        try {
            emit(Resource.Loading<List<Review>>())
            val res = repository.reviews(id, page).toReviews()
            emit(Resource.Success<List<Review>>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Review>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Review>>("Couldn't reach server. Check your internet connection."))
        }
    }
}