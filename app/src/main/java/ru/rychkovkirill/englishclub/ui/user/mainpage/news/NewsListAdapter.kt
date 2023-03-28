package ru.rychkovkirill.englishclub.ui.user.mainpage.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Menu
import ru.rychkovkirill.englishclub.domain.models.News

class NewsListAdapter() : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    var newsList = listOf<News>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((News) -> Unit)? = null

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.title)
        val tvDate = itemView.findViewById<TextView>(R.id.date)
        val tvContent = itemView.findViewById<TextView>(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvTitle.text = newsList[position].title
        holder.tvDate.text = newsList[position].created_at
        holder.tvContent.text = newsList[position].content
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}