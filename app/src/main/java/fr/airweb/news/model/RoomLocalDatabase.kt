package fr.airweb.news.model

import androidx.room.*

class RoomLocalDatabase {
    @Dao
    interface NewsDao{
        @Query("SELECT * FROM news")
        fun getAllNews() : List<News>
    }

    @Database(entities = [News::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun newsDao(): NewsDao
    }

    @Entity
    data class News(
        @PrimaryKey val nid: Int,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "date") val date: String?,
        @ColumnInfo(name = "title") val title: String?,
        @ColumnInfo(name = "picture") val picture: String?,
        @ColumnInfo(name = "content") val content: String?,
        @ColumnInfo(name = "dateFormated") val dateFormated: String?
    )
}