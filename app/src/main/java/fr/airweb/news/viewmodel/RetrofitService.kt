package fr.airweb.news.viewmodel

import fr.airweb.news.model.NewsRoomContainer
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("psg.json")
    fun getAllNews(): Call<NewsRoomContainer>
}