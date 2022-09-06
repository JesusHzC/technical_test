package com.example.technicaltest.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImp): MovieRepository

    @Binds
    @Singleton
    abstract fun bindPeopleRepository(peopleRepositoryImpl: PeopleRepositoryImp): PeopleRepository

}