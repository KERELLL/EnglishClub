package ru.rychkovkirill.englishclub.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Menu

class MainListAdapter() : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {

    var menuList = listOf<Menu>(
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date"),
        Menu("Title", "Name", "Date")
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((Menu) -> Unit)? = null

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.title)
        val tvName = itemView.findViewById<TextView>(R.id.name)
        val tvDate = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.tvTitle.text = menuList[position].title
        holder.tvName.text = menuList[position].name
        holder.tvDate.text = menuList[position].date
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(menuList[position])
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}

