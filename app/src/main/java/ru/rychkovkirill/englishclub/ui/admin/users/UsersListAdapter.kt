package ru.rychkovkirill.englishclub.ui.admin.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.models.Menu
import ru.rychkovkirill.englishclub.domain.models.User

class UsersListAdapter() : RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

    var userList = listOf<User>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelectListener: ((User) -> Unit)? = null

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername = itemView.findViewById<TextView>(R.id.username)
        val tvName = itemView.findViewById<TextView>(R.id.name)
        val tvEmail = itemView.findViewById<TextView>(R.id.email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvName.text = userList[position].first_name + " " + userList[position].last_name
        holder.tvUsername.text = userList[position].username
        holder.tvEmail.text = userList[position].email
        holder.itemView.setOnClickListener {
            onItemSelectListener?.invoke(userList[position])
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}