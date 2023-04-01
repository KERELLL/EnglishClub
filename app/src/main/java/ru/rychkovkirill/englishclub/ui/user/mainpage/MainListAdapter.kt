package ru.rychkovkirill.englishclub.ui.user.mainpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Menu
import ru.rychkovkirill.englishclub.domain.models.Reservation

class MainListAdapter() : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {

    var reservationList = listOf<Reservation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((Reservation) -> Unit)? = null
    var onApproveClickListener: ((Reservation) -> Unit)? = null

    var isApprovedShit: Boolean = false

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.title)
        val tvName = itemView.findViewById<TextView>(R.id.name)
        val tvDate = itemView.findViewById<TextView>(R.id.date)
        val tvUser = itemView.findViewById<TextView>(R.id.email)
        val tvApprove = itemView.findViewById<TextView>(R.id.approve)
        val approveButton = itemView.findViewById<TextView>(R.id.approvekButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserv, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.tvTitle.text = reservationList[position].shift.name
        holder.tvName.text = reservationList[position].shift.number.toString()
        holder.tvDate.text = reservationList[position].shift.start_date.split('T')[0] + " - " + reservationList[position].shift.end_date.split('T')[0]
        holder.tvUser.text = reservationList[position].email
        if(reservationList[position].is_approved){
            holder.tvApprove.text = "Одобрена"
            holder.approveButton.isVisible = false
        }else{
            holder.approveButton.isVisible = true
            holder.tvApprove.text = "Неодобрена"
        }
        holder.approveButton.setOnClickListener {
            onApproveClickListener?.invoke(reservationList[position])
        }


        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(reservationList[position])
        }
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }
}

