package fr.airweb.news.viewmodel

import fr.airweb.news.model.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("/psg.json")
    fun getAllNews(): Call<List<News>>


    companion object {
        var retrofitService: RetrofitService? = null

        fun create() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://airweb-demo.airweb.fr/psg")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}