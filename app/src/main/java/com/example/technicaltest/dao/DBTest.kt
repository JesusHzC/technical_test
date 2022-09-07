package com.example.technicaltest.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.technicaltest.dao.converters.Converters
import com.example.technicaltest.dao.entities.*
import com.example.technicaltest.domain.gallery.Gallery
import com.example.technicaltest.domain.location.Location
import com.example.technicaltest.domain.movie.Image
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.movie.People

@Database(
    entities = [
        Movie::class,
        Image::class,
        People::class,
        Gallery::class
    ],
    version = 9
)
@TypeConverters(Converters::class)
abstract class DBTest : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getImageDao(): ImageDao
    abstract fun getPeopleDao(): PeopleDao
    abstract fun getGalleryDao(): GalleryDao

    companion object {
        @JvmStatic
        fun newInstance(context: Context): DBTest {
            return Room.databaseBuilder(context, DBTest::class.java, "DBTest")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}