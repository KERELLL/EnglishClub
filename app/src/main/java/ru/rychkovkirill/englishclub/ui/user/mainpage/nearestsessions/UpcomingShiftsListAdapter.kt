package ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Menu
import ru.rychkovkirill.englishclub.domain.models.Shift
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter

class UpcomingShiftsListAdapter : RecyclerView.Adapter<UpcomingShiftsListAdapter.ShiftViewHolder>() {

    var upcomingShiftsList = listOf<Shift>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((Shift) -> Unit)? = null

    class ShiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.title)
        val tvName = itemView.findViewById<TextView>(R.id.name)
        val tvDate = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return ShiftViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.tvTitle.text = "324"
        holder.tvName.text = upcomingShiftsList[position].name
        holder.tvDate.text = upcomingShiftsList[position].start_date + "-" + upcomingShiftsList[position].end_date
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(upcomingShiftsList[position])
        }
    }

    override fun getItemCount(): Int {
        return upcomingShiftsList.size
    }
}