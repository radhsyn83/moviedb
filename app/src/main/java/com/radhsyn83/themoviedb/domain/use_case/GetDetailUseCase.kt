package com.radhsyn83.themoviedb.domain.use_case

import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.data.remote.dto.toDetail
import com.radhsyn83.themoviedb.domain.model.Detail
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Detail>> = flow {
        try {
            emit(Resource.Loading<Detail>())
            val res = repository.detail(id).toDetail()
            emit(Resource.Success<Detail>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<Detail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<Detail>("Couldn't reach server. Check your internet connection."))
        }
    }
}