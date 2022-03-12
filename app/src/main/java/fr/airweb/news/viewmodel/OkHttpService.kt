package fr.airweb.news.viewmodel

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class OkHttpService {

    private val client = OkHttpClient()

    fun run() {
        val request = Request.Builder()
            .url("https://airweb-demo.airweb.fr/psg/psg.json")
            .build()

        client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                Log.d("juif",response.body().toString())
            }
    }
}
