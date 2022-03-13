package fr.airweb.news.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
    interface NewsDao{
        @Query("SELECT * FROM news_table ORDER BY title ASC")
        fun getAllNews() : Flow<List<News>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(news : News) : Long

        @Query("DELETE FROM news_table")
        suspend fun deleteAllNews()

        @Query("SELECT * FROM news_table WHERE type = 'hot'")
        fun getHotNews() : Flow<List<News>>

        @Query("SELECT * FROM news_table WHERE type = 'actualité'")
        fun getActuNews() : Flow<List<News>>

        @Query("SELECT * FROM news_table WHERE type = 'news'")
        fun getNewsNews() : Flow<List<News>>




    }

    @Entity(tableName = "news_table")
    class News(
        @PrimaryKey @ColumnInfo(name = "nid") val id: Int,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "date") val date: String?,
        @ColumnInfo(name = "title") val title: String?,
        @ColumnInfo(name = "picture") val picture: String?,
        @ColumnInfo(name = "content") val content: String?,
        @ColumnInfo(name = "dateformated") val dateFormated: String?
    )


data class NewsContainer(
    var news : List<News>? = null
)