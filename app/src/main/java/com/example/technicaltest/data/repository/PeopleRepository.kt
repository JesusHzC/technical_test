package com.example.technicaltest.data.repository

import com.example.technicaltest.domain.movie.Image
import com.example.technicaltest.domain.movie.People
import com.example.technicaltest.domain.util.Resource

interface PeopleRepository {
    suspend fun getPopularPerson() : Resource<People>
    suspend fun getImagesPerson(id: Int) : Resource<List<Image>>
}