package fr.airweb.news.viewmodel

import androidx.lifecycle.*
import fr.airweb.news.model.News
import fr.airweb.news.model.NewsRepository
import kotlinx.coroutines.launch


    class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

        // Using LiveData and caching what allNewss returns has several benefits:
        // - We can put an observer on the data (instead of polling for changes) and only update the
        //   the UI when the data actually changes.
        // - Repository is completely separated from the UI through the ViewModel.
        val allNews: LiveData<List<News>> = repository.allNews.asLiveData()

        /**
         * Launching a new coroutine to insert the data in a non-blocking way
         */
        fun insert(news: News) = viewModelScope.launch {
            repository.insert(news)
        }
    }

    class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

