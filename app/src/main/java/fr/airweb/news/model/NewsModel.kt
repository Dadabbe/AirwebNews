package fr.airweb.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("title") @Expose var title: String,
    @SerializedName("content") @Expose var content: String,
    @SerializedName("date") @Expose var date: Date,
    @SerializedName("picture") @Expose var picture: String
)

data class NewsContainer(
    var news : List<News>? = null
)