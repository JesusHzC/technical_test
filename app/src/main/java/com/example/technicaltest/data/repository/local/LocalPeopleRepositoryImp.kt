package com.example.technicaltest.data.repository.local

import com.example.technicaltest.dao.DBTest
import com.example.technicaltest.domain.movie.People
import javax.inject.Inject

class LocalPeopleRepositoryImp @Inject constructor(
    private val dbTest: DBTest
) : LocalPeopleRepository {

    override suspend fun getPeople(): People {
        return dbTest.getPeopleDao().get()
    }

    override suspend fun savePeople(people: People) {
        dbTest.getPeopleDao().insert(people)
    }

    override suspend fun deletePeople() {
        dbTest.getPeopleDao().deleteAll()
    }
}