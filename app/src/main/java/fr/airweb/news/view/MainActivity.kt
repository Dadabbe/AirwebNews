package fr.airweb.news.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import fr.airweb.news.R
import fr.airweb.news.model.NewsApplication
import fr.airweb.news.model.NewsContainer
import fr.airweb.news.model.NewsDao
import fr.airweb.news.viewmodel.NewsListAdapter
import fr.airweb.news.viewmodel.NewsViewModel
import fr.airweb.news.viewmodel.NewsViewModelFactory
import fr.airweb.news.viewmodel.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@GlideModule
class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

    //Webservice url
    companion object {
        const val BASE_WEBSERVICE_URL = "https://airweb-demo.airweb.fr/psg/"
    }

    private val newNewsActivityRequestCode = 1
    private val newsViewModel : NewsViewModel by viewModels {
        NewsViewModelFactory((application as NewsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Retrofit Webservice Call Setup
        val retro = Retrofit.Builder()
            .baseUrl(BASE_WEBSERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retro.create(RetrofitService::class.java)
        val newsRequest = service.getAllNews()

        //RecyclerView Setup
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NewsListAdapter()
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)

        //NewsViewModel
        //Observer Try
        //newsViewModel.allNews.observe(this, Observer{
        //        news -> news.let { adapter.submitList(it) }
        //})


        newsRequest.enqueue(object : Callback<NewsContainer> {
            //Retrofit Webservice Call to populate Local Room Database, only if connected to Internet
            override fun onResponse(call: Call<NewsContainer>, response: Response<NewsContainer>) {
                val body = response.body()
                val allNews = body?.news
                //val newsDao : NewsDao
                for (c in allNews?.indices!!){
                    Log.v(allNews[c].title,allNews[c].content!!)
                    CoroutineScope(Dispatchers.IO).launch {
                        saveImage(
                            Glide.with(this@MainActivity)
                            .asBitmap()
                            .load(allNews[c].picture)
                                .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                                .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                                .submit()
                                .get(),
                            c,
                            applicationContext)
                    }
                    newsViewModel.insert(allNews[c])
                }
                Toast.makeText(applicationContext,"Connecté à Internet",Toast.LENGTH_SHORT).show()
            }

            //if there is no Internet Access, the call will fail and we will use this
            override fun onFailure(call: Call<NewsContainer>, t: Throwable) {
                Toast.makeText(applicationContext,"Pas d'accès à Internet",Toast.LENGTH_SHORT).show()
            }
    })
}
}

//Image Downloading Functions

fun saveImage(image: Bitmap, c : Int, context: Context): String? {
    var savedImagePath: String? = null
    val imageFileName = "JPEG_$c.jpg"
    val storageDir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + "/AIRWEB_NEWS"
    )
    var success = true
    if (!storageDir.exists()) {
        success = storageDir.mkdirs()
    }
    if (success) {
        Log.v("Oui","Réussite de la création")
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.getAbsolutePath()
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        galleryAddPic(savedImagePath,context)
    }
    return savedImagePath
}

fun galleryAddPic(imagePath: String?, context : Context) {
    imagePath?.let { path ->
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(path)
        Log.v("Path",f.toString())
        val contentUri: Uri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }
}
