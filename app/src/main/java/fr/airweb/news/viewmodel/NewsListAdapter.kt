package fr.airweb.news.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.airweb.news.model.News
import com.bumptech.glide.Glide
import android.content.Context
import android.graphics.drawable.Drawable

import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import fr.airweb.news.R


class NewsListAdapter : ListAdapter<News, NewsListAdapter.NewsViewHolder>(NewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title)
        holder.bind(current.picture)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsItemView: TextView = itemView.findViewById(R.id.article_textView)
        private val newsImageView: ImageView = itemView.findViewById(R.id.article_imageView)

        fun bind(text: String?) {
            newsItemView.text = text
            //val options: RequestOptions = RequestOptions()
            //    .centerCrop()
            //    .placeholder(fr.airweb.news.R.mipmap.ic_launcher_round)
            //    .error(fr.airweb.news.R.mipmap.ic_launcher_round)

            //Glide.with().load().apply(options).into<Target<Drawable>>(newsImageView)
        }


        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return NewsViewHolder(view)
            }
        }
    }

    class NewsComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }
    }
}