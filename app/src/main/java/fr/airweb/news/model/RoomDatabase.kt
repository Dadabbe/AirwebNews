package fr.airweb.news.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(News::class), version = 1, exportSchema = false)
    public abstract class NewsRoomDatabase : RoomDatabase() {

        abstract fun newsDao(): NewsDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: NewsRoomDatabase? = null

            fun getDatabase(context: Context, scope: CoroutineScope): NewsRoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsRoomDatabase::class.java,
                        "news_database"
                    )
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //Inutile ici
                    //populateDatabase(database.newsDao())
                }
            }
        }

        //Inutile dans mon cas
        //suspend fun populateDatabase(newsDao: NewsDao) {
        //
        //
        //}
    }

}
