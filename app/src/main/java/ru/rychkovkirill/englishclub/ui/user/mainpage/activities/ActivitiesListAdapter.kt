package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Shift
import ru.rychkovkirill.englishclub.domain.models.Task
import ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions.UpcomingShiftsListAdapter

class ActivitiesListAdapter : RecyclerView.Adapter<ActivitiesListAdapter.TaskViewHolder>() {

    var tasksList = listOf<Task>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((Task) -> Unit)? = null

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.title)
        val tvDescription = itemView.findViewById<TextView>(R.id.description)
        val tvPoints = itemView.findViewById<TextView>(R.id.points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.tvTitle.text = tasksList[position].title
        holder.tvPoints.text = tasksList[position].points.toString() + " Очков"
        holder.tvDescription.text = tasksList[position].description
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(tasksList[position])
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

}