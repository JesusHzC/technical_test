package com.example.technicaltest.data.repository.local

import com.example.technicaltest.domain.movie.People

interface LocalPeopleRepository {
    suspend fun getPeople(): People
    suspend fun savePeople(people: People)
    suspend fun deletePeople()
}