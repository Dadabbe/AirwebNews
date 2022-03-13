package fr.airweb.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.airweb.news.R
import fr.airweb.news.model.NewsContainer
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


        newsRequest.enqueue(object : Callback<NewsContainer> {
            //Retrofit Webservice Call to populate Local Room Database, only if connected to Internet
            override fun onResponse(call: Call<NewsContainer>, response: Response<NewsContainer>) {
                val body = response.body()
                val allNews = body?.news
                for (c in allNews?.indices!!){
                    Log.v(allNews[c].title,allNews[c].content!!)
                }
            }
            //if there is no Internet Access, the call will fail and we will use this
            override fun onFailure(call: Call<NewsContainer>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
    })
}
}

