package com.example.technicaltest.data.repository

import com.example.technicaltest.data.remote.MovieApi
import com.example.technicaltest.domain.movie.Image
import com.example.technicaltest.domain.movie.People
import com.example.technicaltest.domain.util.Resource
import javax.inject.Inject

class PeopleRepositoryImp @Inject constructor(
    private val api: MovieApi
) : PeopleRepository {

    override suspend fun getPopularPerson(): Resource<People> {
        return try {
            Resource.Success(
                data = api.getPopularPerson().people[0]
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }

    override suspend fun getImagesPerson(id: Int): Resource<List<Image>> {
        return try {
            Resource.Success(
                data = api.getPersonImages(id).profiles
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }

}