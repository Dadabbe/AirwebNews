package fr.airweb.news.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(News::class), version = 1, exportSchema = false)
    public abstract class NewsRoomDatabase : RoomDatabase() {

        abstract fun newsDao(): NewsDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: NewsRoomDatabase? = null

            fun getDatabase(context: Context): NewsRoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsRoomDatabase::class.java,
                        "news_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }
