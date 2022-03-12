package fr.airweb.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
    @SerializedName("nid") @Expose var id: Int? = null,
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("date") @Expose var date: Date,
    @SerializedName("title") @Expose var title: String,
    @SerializedName("picture") @Expose var picture: String,
    @SerializedName("content") @Expose var content: String,
    @SerializedName("dateformated") @Expose var dateFormated: String,
)

data class NewsContainer(
    var news : List<News>? = null
)