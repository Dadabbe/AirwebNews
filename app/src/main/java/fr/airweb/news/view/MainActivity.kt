package fr.airweb.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.airweb.news.R
import fr.airweb.news.model.NewsRoomContainer
import fr.airweb.news.viewmodel.RetrofitService
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    //Retrofit Webservice Call Setup
    companion object {
        const val BASE_WEBSERVICE_URL = "https://airweb-demo.airweb.fr/psg/"
    }
    val retro = Retrofit.Builder()
        .baseUrl(BASE_WEBSERVICE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service = retro.create(RetrofitService::class.java)
    val newsRequest = service.getAllNews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Retrofit Webservice Call to populate Local Room Database, only if connected to Internet
        newsRequest.enqueue(object : Callback<NewsRoomContainer> {
            override fun onResponse(call: Call<NewsRoomContainer>, response: Response<NewsRoomContainer>) {
                val allNews = response.body()
                Log.v("Rep",response.code().toString())
                Log.v("TOTU",allNews.toString())
                Log.v("TOTU", allNews?.news?.get(1)?.title.toString())
                //for (c in allCountry!!)
                //    Log.v(
                //        MainActivity::class.simpleName,
                //        "NAME: ${c.content} \n CAPITAL: ${c.title} \n Language: ${c.dateFormated} "
                //    )
            }
            override fun onFailure(call: Call<NewsRoomContainer>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
    })
}
}

