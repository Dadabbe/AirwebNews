package fr.airweb.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.airweb.news.R
import fr.airweb.news.model.News
import fr.airweb.news.viewmodel.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val URL_COUNTRY_API = "https://airweb-demo.airweb.fr/psg/"
    }

    val retro = Retrofit.Builder()
        .baseUrl(URL_COUNTRY_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service = retro.create(RetrofitService::class.java)
    val newsRequest = service.getAllNews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRequest.enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                val allCountry = response.body()
                for (c in allCountry!!)
                    Log.v(
                        MainActivity::class.simpleName,
                        "NAME: ${c.content} \n CAPITAL: ${c.title} \n Language: ${c.dateFormated} "
                    )
            }
            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
    })
}
}

