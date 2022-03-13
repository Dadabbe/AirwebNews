package fr.airweb.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.airweb.news.R
import fr.airweb.news.model.News
import fr.airweb.news.model.NewsContainer
import fr.airweb.news.viewmodel.RetrofitService
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

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
            override fun onResponse(call: Call<NewsContainer>, response: Response<NewsContainer>) {
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
            override fun onFailure(call: Call<NewsContainer>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
    })
}
}

