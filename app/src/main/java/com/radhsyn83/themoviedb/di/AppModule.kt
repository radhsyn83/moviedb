package com.radhsyn83.themoviedb.di

import com.radhsyn83.themoviedb.common.NetworkInstance
import com.radhsyn83.themoviedb.data.remote.MovieDBApi
import com.radhsyn83.themoviedb.data.repository.MovieRepositoryImpl
import com.radhsyn83.themoviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAPINew() : MovieDBApi {
        return NetworkInstance.api()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieDBApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}