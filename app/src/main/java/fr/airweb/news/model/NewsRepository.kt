package fr.airweb.news.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class NewsRepository (private val newsDao: NewsDao) {

    val allNews: Flow<List<News>> = newsDao.getAllNews()
    val hotNews: Flow<List<News>> = newsDao.getHotNews()
    val actuNews: Flow<List<News>> = newsDao.getActuNews()
    val newsNews: Flow<List<News>> = newsDao.getNewsNews()

    @WorkerThread
    suspend fun insert(news: News) {
        newsDao.insert(news)
    }
}