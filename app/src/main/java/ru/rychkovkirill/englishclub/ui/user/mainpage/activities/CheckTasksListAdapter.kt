package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Response
import ru.rychkovkirill.englishclub.domain.models.Task

class CheckTasksListAdapter : RecyclerView.Adapter<CheckTasksListAdapter.ResponseViewHolder>() {

    var responsesList = listOf<Response>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((Response) -> Unit)? = null

    class ResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.findViewById<TextView>(R.id.date)
        val tvEmail = itemView.findViewById<TextView>(R.id.email)
        val tvChecked = itemView.findViewById<TextView>(R.id.is_checked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_response, parent, false)
        return ResponseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        holder.tvDate.text = responsesList[position].response_time
        holder.tvEmail.text = responsesList[position].user_email
        if (responsesList[position].is_checked) {
            holder.tvChecked.text = "Работа проверена"
        } else {
            holder.tvChecked.text = "Работа непроверена"
        }
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(responsesList[position])
        }
    }

    override fun getItemCount(): Int {
        return responsesList.size
    }
}