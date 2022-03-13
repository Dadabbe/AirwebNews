package fr.airweb.news.model

import androidx.room.*

class RoomLocalDatabase {

    @Dao
    interface NewsDao{
        @Query("SELECT * FROM news_table")
        fun getAllNews() : List<NewsRoom>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insert(news : NewsRoom) : Long

        @Query("DELETE FROM news_table")
        fun deleteAllNews() : Int
    }

    @Database(entities = [NewsRoom::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun newsDao(): NewsDao
    }

    @Entity(tableName = "news_table")
    class NewsRoom(
        @PrimaryKey @ColumnInfo(name = "nid") val id: Int,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "date") val date: String?,
        @ColumnInfo(name = "title") val title: String?,
        @ColumnInfo(name = "picture") val picture: String?,
        @ColumnInfo(name = "content") val content: String?,
        @ColumnInfo(name = "dateformated") val dateFormated: String?
    )
}

data class NewsRoomContainer(
    var news : List<RoomLocalDatabase.NewsRoom>? = null
)