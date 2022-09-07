package com.example.technicaltest.data.repository

import com.example.technicaltest.data.repository.local.*
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

    @Binds
    @Singleton
    abstract fun bindLocalMovieRepository(localMovieRepositoryImpl: LocalMovieRepositoryImp): LocalMovieRepository

    @Binds
    @Singleton
    abstract fun bindLocalPeopleRepository(localPeopleRepositoryImpl: LocalPeopleRepositoryImp): LocalPeopleRepository

    @Binds
    @Singleton
    abstract fun bindLocalImagesRepository(localImagesRepositoryImpl: LocalImagesRepositoryImp): LocalImagesRepository

    @Binds
    @Singleton
    abstract fun bindLocalGalleryRepository(localGalleryRepositoryImpl: LocalGalleryRepositoryImp): LocalGalleyRepository

}