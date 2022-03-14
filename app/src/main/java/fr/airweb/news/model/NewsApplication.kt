package fr.airweb.news.model

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NewsApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NewsRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { NewsRepository(database.newsDao()) }
}