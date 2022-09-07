package com.example.technicaltest.data.repository.local

import com.example.technicaltest.dao.DBTest
import com.example.technicaltest.domain.movie.Image
import javax.inject.Inject


class LocalImagesRepositoryImp @Inject constructor(
    private val dbTest: DBTest
) : LocalImagesRepository {

    override suspend fun getImages(): List<Image> {
        return dbTest.getImageDao().getAllImages()
    }

    override suspend fun saveImages(images: List<Image>) {
        dbTest.getImageDao().insertAll(images)
    }

    override suspend fun deleteImages() {
        dbTest.getImageDao().deleteAllImages()
    }


}