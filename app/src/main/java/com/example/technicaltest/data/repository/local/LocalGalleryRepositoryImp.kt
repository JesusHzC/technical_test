package com.example.technicaltest.data.repository.local

import com.example.technicaltest.dao.DBTest
import com.example.technicaltest.domain.gallery.Gallery
import javax.inject.Inject


class LocalGalleryRepositoryImp @Inject constructor(
    private val dbTest: DBTest
) : LocalGalleyRepository {
    override suspend fun getAllImages(): List<Gallery> {
        return dbTest.getGalleryDao().getAll()
    }

    override suspend fun saveImages(gallery: List<Gallery>) {
        dbTest.getGalleryDao().insertAll(gallery)
    }

    override suspend fun deleteAllImages() {
        dbTest.getGalleryDao().deleteAll()
    }

    override suspend fun saveImage(gallery: Gallery) {
        dbTest.getGalleryDao().insert(gallery)
    }
}