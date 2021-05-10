package com.example.roomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.OnRvClick
import com.example.roomdb.R
import com.example.roomdb.models.User

class UserAdapter(
    private val userList: ArrayList<User>,
    private val onRvClick: OnRvClick
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhno)
        val tvAddressTag: TextView = itemView.findViewById(R.id.tvAddressTag)
        val btnCall: ImageButton = itemView.findViewById(R.id.btnCall)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        (parent.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater).inflate(R.layout.user_card, parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvId.text = userList[position].name[0].toString()
        holder.tvName.text = userList[position].name
        holder.tvAddress.text = userList[position].address
        holder.tvPhone.text = userList[position].phone
        holder.tvAge.text = userList[position].age
        if (userList[position].address == null) {
            holder.tvAddressTag.visibility = View.GONE
            holder.tvAddress.visibility = View.GONE
        }
        holder.btnCall.setOnClickListener {
            onRvClick.onClick(position)
        }
    }

    override fun getItemCount() = userList.size

}